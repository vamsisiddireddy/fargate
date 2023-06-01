module "module_qa" {
  source          = "./modules"
  cluster_name    = "qa_cluster"
  service1_name   = "qa_service1"
  service2_name   = "qa_service2"
  container1_name = "qa_react"
  container2_name = "qa_java"
  family1_name    = "qa_td1"
  family2_name    = "qa_td2"
}