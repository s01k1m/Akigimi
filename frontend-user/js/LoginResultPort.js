class LoginResultPort{
    save({accessToken,refreshToken,userId,userState}) {
        let localStoragePort = new LocalStoragePort();
        localStoragePort.save("accessToken", accessToken);
        localStoragePort.save("refreshToken", refreshToken);
        localStoragePort.save("userId", userId);
        localStoragePort.save("userState", userState);
    }
    removeDoubleQuotation(str) {
        return str.replace('"', '');
    }

    getAccessToken() {
        return this.removeDoubleQuotation(new LocalStoragePort().get("accessToken"))
    }

    getRefreshToken() {
        return this.removeDoubleQuotation(new LocalStoragePort().get("accessToken"))
    }

    getUserId() {
        return this.removeDoubleQuotation(new LocalStoragePort().get("accessToken"))
    }


    getUserState() {
        return this.removeDoubleQuotation(new LocalStoragePort().get("accessToken"))
    }
}