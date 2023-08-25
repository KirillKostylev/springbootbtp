## Springbootbtp ##

Pet project to play around with SAP BTP and provided services

***Stack***
* Java 11
* Spring boot 2.7
* PostgreSQL
* Liquibase

***Deploy app***

1. Create manifest.yml
2. ```cf login https://api.cf.us10-001.hana.ondemand.com --sso```
3. ```cf push```


***Setup XSUAA***

Create and bind xs-security.json config (define scopes here)

``` cf create-service xsuaa application <xsuaa-service_name> -c xs-security.json ```

***Setup SAAS Regestry service***
1. Create config.json
2. Update XSUAA config ```tenant-mode: shared``` and recreate XSUAA instance
3. Add new endpoits to subscribe and unsubscribe tenants

***Create subscriber***
1. Create a new subaccount
2. Subscribe on app (app info in SAAS config.json)
3. Create a new route ```cf map-route <aprouter_name> cfapps.us10-001.hana.ondemand.com --hostname {consumer-subdomain}-{approuter-domain}``` 
4. Add a new role and assign to the user

***Connect to db***
1. To connect to db you need to open ssh tunnel
```cf ssh -L <local_port>:<db_hostname>:<cloud_port> <app_name>```. Example ```cf ssh -L 54321:postgres-ea230326-5167-4051-8774-04a4f025798d.cqryblsdrbcs.us-east-1.rds.amazonaws.com:6930 helloworld```
2. Connect to db. All data (username, password, dbname, hostname) is part of db config on BTP 

***Debug app on cloud***
1. ```cf ssh <app_name>```
2. ```app/META-INF/.sap_java_buildpack/sap_machine_jre/bin/jcmd 7 VM.start_java_debugging```
3. ```cf ssh -N -T -L 8000:localhost:8000 <application name>```
4. Create remote debug config

***Postmant test***
1. Setup OAuth 2.0 authorization:
   * Grand Type - Password Credentional
   * Access Token URL - {VCAP_SERVICES.<xsuua_name>.credentials.url}/oauth/token
   * Client ID
   * Client Secret
   * Scope ({appname}.{scope})
   * Username + Password
2. Add x-zid header to specify tenantID (part of authorization)
3. Make a http call
