/**
 *
 * Copyright (c) 2014, the Railo Company Ltd. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either 
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this library.  If not, see <http://www.gnu.org/licenses/>.
 * 
 **/
package lucee.runtime.db.driver.state;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import lucee.runtime.PageContext;
import lucee.runtime.PageContextImpl;
import lucee.runtime.db.driver.CallableStatementProxy;
import lucee.runtime.db.driver.ConnectionProxy;
import lucee.runtime.debug.ActiveQuery;
import lucee.runtime.engine.ThreadLocalPageContext;

public class StateCallableStatement extends CallableStatementProxy {

	public StateCallableStatement(ConnectionProxy conn,CallableStatement prepareCall, String sql) {
		super(conn, prepareCall,sql);
	}
	

	@Override
	public boolean execute(String sql) throws SQLException {
		return StateUtil.execute(ThreadLocalPageContext.get(),stat, sql);
	}

	@Override
	public boolean execute(PageContext pc, String sql) throws SQLException {
		return StateUtil.execute(pc,stat, sql);
	}

	@Override
	public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
		return StateUtil.execute(ThreadLocalPageContext.get(), stat, sql,autoGeneratedKeys);
	}

	@Override
	public boolean execute(PageContext pc, String sql, int autoGeneratedKeys) throws SQLException {
		return StateUtil.execute(pc, stat, sql,autoGeneratedKeys);
	}

	@Override
	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		return StateUtil.execute(ThreadLocalPageContext.get(), stat, sql,columnIndexes);
	}

	@Override
	public boolean execute(PageContext pc, String sql, int[] columnIndexes) throws SQLException {
		return StateUtil.execute(pc, stat, sql,columnIndexes);
	}

	@Override
	public boolean execute(String sql, String[] columnNames) throws SQLException {
		return StateUtil.execute(ThreadLocalPageContext.get(), stat, sql,columnNames);
	}

	@Override
	public boolean execute(PageContext pc, String sql, String[] columnNames) throws SQLException {
		return StateUtil.execute(pc, stat, sql,columnNames);
	}
	@Override
	public ResultSet executeQuery(String sql) throws SQLException {
		return StateUtil.executeQuery(ThreadLocalPageContext.get(), stat,sql);
	}
	@Override
	public ResultSet executeQuery(PageContext pc, String sql) throws SQLException {
		return StateUtil.executeQuery(pc, stat,sql);
	}
	
	@Override
	public int executeUpdate(String sql) throws SQLException {
		return StateUtil.executeUpdate(ThreadLocalPageContext.get(), stat, sql);
	}

	@Override
	public int executeUpdate(PageContext pc, String sql) throws SQLException {
		return StateUtil.executeUpdate(pc, stat, sql);
	}

	@Override
	public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
		return StateUtil.executeUpdate(ThreadLocalPageContext.get(), stat, sql,autoGeneratedKeys);
	}

	@Override
	public int executeUpdate(PageContext pc, String sql, int autoGeneratedKeys) throws SQLException {
		return StateUtil.executeUpdate(pc, stat, sql,autoGeneratedKeys);
	}

	@Override
	public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
		return StateUtil.executeUpdate(ThreadLocalPageContext.get(), stat, sql,columnIndexes);
	}

	@Override
	public int executeUpdate(PageContext pc, String sql, int[] columnIndexes) throws SQLException {
		return StateUtil.executeUpdate(pc, stat, sql,columnIndexes);
	}

	@Override
	public int executeUpdate(String sql, String[] columnNames) throws SQLException {
		return StateUtil.executeUpdate(ThreadLocalPageContext.get(), stat, sql,columnNames);
	}

	@Override
	public int executeUpdate(PageContext pc, String sql, String[] columnNames) throws SQLException {
		return StateUtil.executeUpdate(pc, stat, sql,columnNames);
	}
	
	@Override
	public boolean execute() throws SQLException {
		return StateUtil.execute(ThreadLocalPageContext.get(), stat, sql);
	}
	
	@Override
	public boolean execute(PageContext pc) throws SQLException {
		return StateUtil.execute(pc, stat, sql);
	}

	@Override
	public ResultSet executeQuery() throws SQLException {
		return StateUtil.executeQuery(ThreadLocalPageContext.get(), stat, sql);
	}

	@Override
	public ResultSet executeQuery(PageContext pc) throws SQLException {
		return StateUtil.executeQuery(pc, stat, sql);
	}

	@Override
	public int executeUpdate() throws SQLException {
		return StateUtil.executeUpdate(ThreadLocalPageContext.get(), stat, sql);
	}

	@Override
	public int executeUpdate(PageContext pc) throws SQLException {
		return StateUtil.executeUpdate(pc, stat, sql);
	}

	protected void setActiveStatement(PageContextImpl pc,Statement stat, String sql) {
		pc.setActiveQuery(new ActiveQuery(sql,System.currentTimeMillis()));
	}

}
