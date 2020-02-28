namespace java org.example.thrift

exception InvalidKeyException {
    1: i32 code,
    2: string description
}

struct Response {
    1:i32 statusCode,
    2:string message
}

service CrossPlatformService {

    Response putRequest(1:string key, 2:string value) throws (1: InvalidKeyException e),

    Response getRequest(1:string key) throws (1: InvalidKeyException e),

    Response deleteRequest(1:string key) throws (1:InvalidKeyException e),

    bool ping()
}