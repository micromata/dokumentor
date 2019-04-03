package de.micromata.dokumentor.xml.classpackage.technically;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import de.micromata.dokumentor.xml.classpackage.config.GitServiceConfig;
import java.io.File;
import java.nio.file.Files;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.transport.*;
import org.eclipse.jgit.util.FS;

/** Created by Mirco Nuhn - Micromata Gmbh */
public class GitService {

  private static GitServiceConfig gitServiceConfig;

  private File sshPrivateKey;
  private File sshPublicKey;

  CredentialsProvider credentialsProvider;

  public void cloneRepo() {
    deleteDir(gitServiceConfig.getFileRepository());
    // clone the repo!
    boolean cloneAll = true;
    CloneCommand command = Git.cloneRepository();
    configureCommand(command);
    command =
        command
            .setCredentialsProvider(credentialsProvider)
            .setCloneAllBranches(cloneAll)
            .setURI(gitServiceConfig.getGitRepositoryUrl())
            .setDirectory(gitServiceConfig.getFileRepository())
            .setRemote(gitServiceConfig.getRemoteName());

    try {
      Files.deleteIfExists(gitServiceConfig.getFileRepository().toPath());

      Git git = command.call();

      if (GitService.gitServiceConfig.getTagName() != null) {
        git.checkout().setName(gitServiceConfig.getTagName()).call();
      }
    } catch (Throwable e) {
      throw new RuntimeException(
          "Failed to command remote repo "
              + gitServiceConfig.getGitRepositoryUrl()
              + " due: "
              + e.getMessage());
    }
  }

  public <C extends GitCommand> void configureCommand(TransportCommand<C, ?> command) {
    if (sshPrivateKey != null) {

      credentialsProvider =
          createCredentialsProvider(
              gitServiceConfig.getGitLoginUsername(), gitServiceConfig.getGitLoginPasswort());

      final CredentialsProvider provider = credentialsProvider;
      command.setTransportConfigCallback(
          new TransportConfigCallback() {

            @Override
            public void configure(Transport transport) {
              if (transport instanceof SshTransport) {
                SshTransport sshTransport = (SshTransport) transport;
                SshSessionFactory sshSessionFactory =
                    new JschConfigSessionFactory() {

                      @Override
                      protected void configure(OpenSshConfig.Host host, Session session) {
                        session.setPort(gitServiceConfig.getGitPort());
                        session.setConfig("StrictHostKeyChecking", "no");

                        UserInfo userInfo = new CredentialsProviderUserInfo(session, provider);
                        session.setUserInfo(userInfo);
                      }

                      @Override
                      protected JSch createDefaultJSch(FS fs) throws JSchException {
                        JSch jsch = super.createDefaultJSch(fs);
                        jsch.removeAllIdentity();
                        String absolutePath = sshPrivateKey.getAbsolutePath();
                        if (sshPublicKey != null) {
                          jsch.addIdentity(
                              absolutePath,
                              sshPublicKey.getAbsolutePath(),
                              gitServiceConfig.getGitLoginPasswort().getBytes());
                        } else {
                          jsch.addIdentity(absolutePath);
                        }
                        return jsch;
                      }
                    };
                sshTransport.setSshSessionFactory(sshSessionFactory);
              }
            }
          });
    }
  }

  public CredentialsProvider createCredentialsProvider(String user, String password) {
    return new UsernamePasswordCredentialsProvider(user, password) {
      @Override
      public boolean isInteractive() {
        return false;
      }

      @Override
      public String toString() {
        return "UsernamePasswordCredentialsProvider{user: "
            + user
            + ", password length: "
            + password.length()
            + "}";
      }
    };
  }

  // TODO: WTF 112
  public static boolean deleteDir(File dir) {
    if (dir.isDirectory()) {
      String[] children = dir.list();
      for (int i = 0; i < children.length; i++) {
        boolean success = deleteDir(new File(dir, children[i]));
        if (!success) {
          return false;
        }
      }
    }
    return dir.delete();
  }

  public GitService(final GitServiceConfig gitServiceConfig) {

    this.gitServiceConfig = gitServiceConfig;

    this.sshPrivateKey = new File(gitServiceConfig.getGitPrivSshId());
    this.sshPublicKey = new File(gitServiceConfig.getGitPubSshId());
  }
}
