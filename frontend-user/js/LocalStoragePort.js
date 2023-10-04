/*
로컬 스토리지에 값을 저장하거나, 값을 가져오는 모듈
 */
class LocalStoragePort{
    /*
    로컬 스토리지에 값을 저장한다.
     */
    save(key, object) {
        localStorage.setItem(key, JSON.stringify(object));
    }

    /*
    로컬 스토리지에서 값을 가져온다.
     */
    get(key) {
        return localStorage.getItem(key);
    }
}
