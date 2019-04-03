package de.micromata.dokumentor.xml.classpackage.config;

/** Created by Mirco Nuhn - Micromata Gmbh */
import java.io.File;

public interface GitServiceConfig {

  String getGitLoginUsername();

  String getGitLoginPasswort();

  String getGitPrivSshId();

  String getGitPubSshId();

  String getGitRepositoryUrl();

  String getRemoteName();

  String getTagName();

  String getFileRepositoryPath();

  File getFileRepository();

  int getGitPort();
}
