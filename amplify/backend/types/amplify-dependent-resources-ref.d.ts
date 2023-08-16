export type AmplifyDependentResourcesAttributes = {
  "analytics": {
    "refreshments": {
      "Id": "string",
      "Region": "string",
      "appName": "string"
    }
  },
  "api": {
    "Refreshments": {
      "GraphQLAPIEndpointOutput": "string",
      "GraphQLAPIIdOutput": "string",
      "GraphQLAPIKeyOutput": "string"
    }
  },
  "auth": {
    "refreshments": {
      "AppClientID": "string",
      "AppClientIDWeb": "string",
      "IdentityPoolId": "string",
      "IdentityPoolName": "string",
      "UserPoolArn": "string",
      "UserPoolId": "string",
      "UserPoolName": "string"
    },
    "userPoolGroups": {
      "adminGroupRole": "string",
      "editorsGroupRole": "string"
    }
  }
}