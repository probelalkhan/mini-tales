package dev.belalkhan.minitales.auth.data

val loginSuccess = "{\n" +
    "    \"data\": {\n" +
    "        \"id\": 1,\n" +
    "        \"fullName\": \"Belal Khan\",\n" +
    "        \"avatar\": \"\",\n" +
    "        \"email\": \"probelalkhan@gmail.com\",\n" +
    "        \"authToken\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJteS1zdG9yeS1hcHAiLCJpc3MiOiJteS1zdG9yeS1hcHAiLCJpZCI6MX0.nJ4J8DoKl6-Kme7lGfBhwCOaQ_B8XCbGnxP1CEpeVas\",\n" +
    "        \"createdAt\": \"2023-11-05T14:50:58.455845\"\n" +
    "    },\n" +
    "    \"message\": \"User successfully logged in\"\n" +
    "}"

val loginFailure = "{\n" +
    "    \"message\": \"Invalid email or password\"\n" +
    "}"
