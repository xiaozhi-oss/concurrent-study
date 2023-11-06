package com.xiaozhi.immutable06;

import com.xiaozhi.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @author xiaozhi
 *
 * DIY 连接池
 */
@Slf4j(topic = "DIYPoolPlus")
public class DIYPoolPlus {

    public static void main(String[] args) {
        PoolPlus pool = new PoolPlus(2);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                String name = Thread.currentThread().getName();
                MockConnectionPlus conn = pool.getConnection();
                log.debug("{} 获取连接 {}", name, conn.getName());
                TimeUtil.sleep(1);
                pool.free(conn);
                log.debug("{} 释放连接 {}", name, conn.getName());
                // 一般是放在 tyr-catch 中的，这里我们测试就不放了
            }, "t" + i).start();
        };
    }
}

/**
 * TODO 连接的增长和收缩
 * 超市等待
 * TODO 连接保话（可用性检测）
 * TODO 分布式 hash
 */
@Slf4j(topic = "Pool")
class PoolPlus {

    private final int size;
    private final MockConnectionPlus[] connections;
    // 连接状态数组，空闲-0，繁忙-1
    private final AtomicIntegerArray states;

    public PoolPlus(int size) {
        this.size = size;
        states = new AtomicIntegerArray(size);
        this.connections = new MockConnectionPlus[size];
        log.debug("============== 创建连接 ===============");
        for (int i = 0; i < size; i++) {
            connections[i] = new MockConnectionPlus("连接" + i);
        }
    }

    // 获取连接
    public MockConnectionPlus getConnection() {
        // 遍历判断是否有空闲的连接
        while (true) {
            for (int i = 0; i < size; i++) {
                if (states.compareAndSet(i, 0, 1)) {
                    return connections[i];
                }
            }
            synchronized (this) {
                try {
                    this.wait(10);      // 10 秒超时
                } catch (InterruptedException e) {
                    throw new RuntimeException("获取连接超时");
                }
            }
        }
    }

    // 归还连接
    public void free(Connection conn) {
        for (int i = 0; i < connections.length; i++) {
            if (conn == connections[i]) {
                // 设置成空闲状态，它不用进行 cas 因为它是此时是获取锁的，所以没有其他线程竞争
                states.set(i, 0);
                synchronized (this) {
                    this.notifyAll();
                }
            }
        }
    }
}

class MockConnectionPlus implements Connection {

    private String name;

    public MockConnectionPlus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public Statement createStatement() throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return null;
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        return null;
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        return null;
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {

    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        return false;
    }

    @Override
    public void commit() throws SQLException {

    }

    @Override
    public void rollback() throws SQLException {

    }

    @Override
    public void close() throws SQLException {

    }

    @Override
    public boolean isClosed() throws SQLException {
        return false;
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        return null;
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {

    }

    @Override
    public boolean isReadOnly() throws SQLException {
        return false;
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {

    }

    @Override
    public String getCatalog() throws SQLException {
        return null;
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {

    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        return 0;
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    @Override
    public void clearWarnings() throws SQLException {

    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return null;
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return null;
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return null;
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {

    }

    @Override
    public void setHoldability(int holdability) throws SQLException {

    }

    @Override
    public int getHoldability() throws SQLException {
        return 0;
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        return null;
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        return null;
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {

    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {

    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return null;
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        return null;
    }

    @Override
    public Clob createClob() throws SQLException {
        return null;
    }

    @Override
    public Blob createBlob() throws SQLException {
        return null;
    }

    @Override
    public NClob createNClob() throws SQLException {
        return null;
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        return null;
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        return false;
    }

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {

    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {

    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        return null;
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        return null;
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        return null;
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        return null;
    }

    @Override
    public void setSchema(String schema) throws SQLException {

    }

    @Override
    public String getSchema() throws SQLException {
        return null;
    }

    @Override
    public void abort(Executor executor) throws SQLException {

    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {

    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        return 0;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}