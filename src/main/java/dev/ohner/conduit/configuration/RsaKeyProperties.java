package dev.ohner.conduit.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "conduit.security.rsa")
public record RsaKeyProperties(
    RSAPublicKey publicKey,
    RSAPrivateKey privateKey
) {
}
