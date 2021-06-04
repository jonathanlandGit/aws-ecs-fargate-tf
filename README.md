# Ecs Fargate, Terraform, Spring Boot demo

This is a demo showing how to deploy a Spring Boot app on ECS Fargate via Terraform.

## Setup

Step 1: Create bucket in s3 for to keep track of remote state (select region and then just leave the other defaults)

e.g., `aws-ecs-fargate-remote-state` 

Step 2: Clone repo and open in IDE

`https://github.com/jonathanlandGit/aws-ecs-fargate-tf.git`

Step 3: Cd into Infrastructure folder and add the name of your bucket to the `infrastructure-prod.config` file and change the region to match your own. 

Step 4: Run the following commands...one after the other
```
terraform init -backend-config="infrastructure-prod.config"
```
```
terraform plan -var-file="production.tfvars"
```
```
terraform apply -var-file="production.tfvars"
```

Step 5: Make some coffee, then return to computer and verify build is done, and your stuff is in AWS. 

Step 6: Register for a domain (the code is configured for this). If not comment out the certificate code in the TF scripts.

Step 7: Include your domain name in production.tf.vars file in Platform and reference your remote state bucket as well

Step 8: Cd into Platform folder and run the following one after the other...
``` 
terraform init -backend-config="platform-prod.config"
```
```
terraform apply -var-file="production.tfvars"
```

Step 9: Cd into Spring Boot app, go to the `deploy.sh` file and add your aws account id in the SERVICE_ID var so that you can deploy to ECR


Step 10: Now run the following commnad in the spring boot/infrastructure folder from the deploy.sh file
``` 
sh deploy.sh build
```

Step 11: Now run the following commnad in the spring boot/infrastructure folder to dockerize the app from the deploy.sh file
``` 
sh deploy.sh dockerize
```
NOTE: if dockerize part hangs, you may need to run this command again or press enter and/or ctrl c

If successfull, you should see somethng like this
``` 
v1: digest: sha256:cb63a11015e77da00288d6ed975a636488ce5eba7cc876e366f7e445bcf985d1 size: 1159
```

Step 12: Now deploy the app from the deploy.sh file
``` 
sh deploy.sh deploy
```

Step 13: Check resources (VPC, subnets, IGW, Elastic IPs, NATgw, EC2, ECS, ECR) and then verify app is running at your domain name and/or ec2 public ip (in my case this is domain name)
``` 
https://springbootapp.jonathanlands.com/test
```


### Workarounds/issues 

1. Make sure the bucket name you choose is unique 

2. Whenever you need to delete resources use terraform destroy starting with the most recent build. 

From application/infrastructure folder
``` 
sh deploy.sh destroy
```

From Platform folder
```
terraform destroy -var-file="production.tfvars"
```

From Infrastructure folder
```
terraform destroy -var-file="production.tfvars"
```



