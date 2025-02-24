variable "TAG" {
  default = "latest"
}

group "default" {
  targets = ["checkout-service"]
}

target "checkout-service" {
  dockerfile = "./src/main/docker/Dockerfile.jvm"
  tags       = ["localhost:7000/checkout-service:${TAG}", "localhost:7000/checkout-service:latest"]
}
