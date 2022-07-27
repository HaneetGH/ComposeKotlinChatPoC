package org.jetbrains.compose.splitpane.demo

class MainViewModel {

    val storeFactory = createStoreFactory()
    val scope = CoroutineScope(Dispatchers.Default)

    val matrixClient = MatrixClient.fromStore(
        storeFactory = storeFactory,
        scope = scope,
    ).getOrThrow() ?: MatrixClient.login(
        baseUrl = Url("https://example.org"),
        identifier = User("username"),
        password = "password",
        storeFactory = storeFactory,
        scope = scope,
    ).getOrThrow()

    matrixClient.startSync()

}