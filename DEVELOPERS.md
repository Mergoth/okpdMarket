#Developer knowledge.

## Run local cloud
From project root folder 

* Build all modules with `gradle clean build` ('webclient' is not included right now) 
* Go to `webclient/` 
* Run `ng build --prod` - to build JS UI part
* Copy `webclient/dist/**` to `gateway/src/main/resources/static`
* Run `sh dev-start.sh`
* Wait 1-2 minutes
* Open `http://$DOCKER_HOST`
    * To check logs: `docker-compose logs SERVICE_NAME_HERE`
* To stop: `docker-compose down`
## Debug microservice

`cd` into folder of needed microservice and run:

`gradle debug bootRun`

