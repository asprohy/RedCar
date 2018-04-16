package com.api;

import java.util.List;

public interface IXbeeMessageConnect {

	/**
	 * APIģʽ��ȡС����Ϣ 
	 */
	public void receiveMessageForAPI();
	
	/**
	 * �㲥ģʽ��ȡС����Ϣ 
	 */
	public void receiveMessageForBroadcast();
	
	/**
	 * ��������С��
	 * @param outputEndPointPara
	 */
	public void sendMessage(SendEndPointPara outputEndPointPara);

	/**
	 * ��������С��
	 * @param outputEndPointParaList
	 */
	public void sendMessages(List<SendEndPointPara> outputEndPointParaList);

	/**
	 * �����˿�
	 * @param portName
	 * @param baudRate
	 */
	public void openComPort(String portName, int baudRate);
	
    /**
     * �رն˿�	
     * @param portName
     */
	public void closeComPort(String portName);
	
	public void clearComPort(String portName);
	
}
