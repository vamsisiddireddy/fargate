module "module_dev" {
  source          = "./modules"
  cluster_name    = "dev_cluster"
  service1_name   = "dev_service1"
  service2_name   = "dev_service2"
  container1_name = "dev_react"
  container2_name = "dev_java"
  family1_name    = "dev_td1"
  family2_name    = "dev_td2"
}
