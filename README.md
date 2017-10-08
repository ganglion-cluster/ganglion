# ganglion

	
Basic technologies such as (XML, SOAP, WSDL) provide means to describe, locate, and invoke services as an entity in its own right. However, these technologies do not give a rich behavioral detail about the role of the service in more complex collaboration. This collaboration includes a sequence of activities and relationships between activities, which build the business process. There are two ways to build this process: service orchestration and service choreography.

## Service orchestration

Service orchestration represents a single centralized executable business process (the orchestrator) that coordinates the interaction among different services. The orchestrator is responsible for invoking and combining the services.

The relationship between all the participating services are described by a single endpoint (i.e., the composite service). The orchestration includes the management of transactions between individual services. Orchestration employs a centralized approach for service composition.

Orchestration

## Service Choreography

Service choreography is a global description of the participating services, which is defined by exchange of messages, rules of interaction and agreements between two or more endpoints. Choreography employs a decentralized approach for service composition.

Choreography

The choreography describes the interactions between multiple services, where as orchestration represents control from one party's perspective. This means that a choreography differs from an orchestration with respect to where the logic that controls the interactions between the services involved should reside.

https://stackoverflow.com/questions/4127241/orchestration-vs-choreography
