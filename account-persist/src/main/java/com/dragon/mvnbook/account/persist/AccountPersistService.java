package com.dragon.mvnbook.account.persist;

/**
 * 帐户持久化服务
 * @author Dragon
 */
public interface AccountPersistService {
	/**
	 * 创建帐户
	 * @param account  传入的未持久化帐户
	 * @return 返回帐户对象
	 * @throws AccountPersistException
	 */
	Account createAccount(Account account) throws AccountPersistException;

	/**
	 * 根据帐户id返回帐户信息
	 * @param id  帐户id
	 * @return 返回帐户信息
	 * @throws AccountPersistException
	 */
	Account readAccount(String id) throws AccountPersistException;

	/**
	 * 更新帐户信息
	 * @param account  传入的新的帐户对象
	 * @return 返回更新过后的帐户对象
	 * @throws AccountPersistException
	 */
	Account updateAccount(Account account) throws AccountPersistException;

	/**
	 * 根据帐户id删除帐户
	 * @param id  帐户id
	 * @throws AccountPersistException
	 */
	void deleteAccount(String id) throws AccountPersistException;

}
