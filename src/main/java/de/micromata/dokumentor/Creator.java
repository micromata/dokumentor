package de.micromata.dokumentor;

import com.fnproject.fn.api.httpgateway.HTTPGatewayContext;

public interface Creator {
  byte[] create(HTTPGatewayContext context);
}
