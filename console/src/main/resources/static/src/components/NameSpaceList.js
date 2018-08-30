import React from 'react'; 
import { Dialog } from '@alifd/next';

/*****************************此行为标记行, 请勿删和修改此行, 文件和组件依赖请写在此行上面, 主体代码请写在此行下面的class中*****************************/
/**
 * 命名空间列表
 */
class NameSpaceList extends React.Component {
    constructor(props) {
        super(props);
        this._namespace = getParams('namespace') || '';
        // this._namespaceShowName = getParams('namespaceShowName') || '';
        this.state = {
            nownamespace: window.nownamespace || this._namespace || '',
            namespaceList: window.namespaceList || []
            // namespaceShowName: window.namespaceShowName || this._namespaceShowName || '',
            // _dingdingLink: "",
            // _forumLink: ""
        };
    }
    componentDidMount() {
        // this.getLink("dingding", "_dingdingLink");
        // this.getLink("discuz", "_forumLink");
    }

    getLink(linkKey, keyName) {
        if (window[keyName] == null) {
            request({
                url: "com.alibaba.nacos.service.getLink",
                data: {
                    linkKey
                },
                success: res => {
                    if (res.code === 200) {
                        window[keyName] = res.data;
                        this.setState({
                            [keyName]: res.data
                        });
                    }
                }
            });
        } else {
            this.setState({
                [keyName]: window[keyName]
            });
        }
    }
    // if (!this.state.namespaceList || this.state.namespaceList.length === 0) {
    //     this.getNameSpaces();
    // } else {
    //     this.calleeParent();
    // }


    /**
      切换namespace
    **/
    changeNameSpace(ns, nsName) {

        this.setnamespace(ns);
        setParams({
            namespace: ns,
            namespaceShowName: nsName
        });
        window.nownamespace = ns;
        window.namespaceShowName = nsName;

        this.calleeParent(true);
        this.props.setNowNameSpace && this.props.setNowNameSpace(nsName, ns);
    }
    calleeParent(needclean = false) {
        this.props.namespaceCallBack && this.props.namespaceCallBack(needclean);
    }
    getNameSpaces() {
        if (window.namespaceList) {
            this.handleNameSpaces(window.namespaceList);
        } else {
            let serverId = getParams('serverId') || 'center';
            request({
                type: 'get',
                url: `/diamond-ops/service/serverId/${serverId}/namespaceInfo`,
                success: res => {
                    if (res.code == 200) {
                        let edasAppId = getParams('edasAppId');
                        if (edasAppId && edasAppId !== '') {
                            console.log("======", edasAppId);
                            request({
                                type: 'get',
                                url: `/diamond-ops/service/namespaceId?edasAppId=${edasAppId}`,
                                success: res => {
                                    this._namespace = res.data;
                                    this.handleNameSpaces([{ namespace: res.data }]);
                                }
                            });
                        } else {
                            this.handleNameSpaces(res.data);
                        }
                    } else {
                        Dialog.alert({
                            language: window.pageLanguage || 'zh-cn',
                            title: aliwareIntl.get('com.alibaba.nacos.component.NameSpaceList.Prompt'),
                            content: res.message
                        });
                    }
                }
            });
        }
    }
    handleNameSpaces(data) {
        let nownamespace = this._namespace || data[0].namespace || '';
        // let namespaceShowName = this._namespaceShowName || data[0].namespaceShowName || '';
        window.namespaceList = data;
        window.nownamespace = nownamespace;
        let namespaceShowName = "";
        for (let i = 0; i < data.length; i++) {
            if (data[i].namespace === nownamespace) {
                namespaceShowName = data[i].namespaceShowName;
                break;
            }
        }
        window.namespaceShowName = namespaceShowName;
        setParams('namespace', nownamespace);
        // setParams('namespaceShowName', namespaceShowName);
        this.props.setNowNameSpace && this.props.setNowNameSpace(namespaceShowName, nownamespace);
        this.setState({
            nownamespace: nownamespace,
            namespaceList: data
        });
        this.calleeParent();
    }
    setnamespace(ns) {
        this.setState({
            nownamespace: ns
        });
    }

    rendernamespace(namespaceList) {

        let nownamespace = this.state.nownamespace; //获得当前namespace
        let namespacesBtn = namespaceList.map((obj, index) => {

            let style = obj.namespace === nownamespace ? { color: '#00C1DE', marginRight: 10, border: 'none', fontSize: 12 } : { color: '#666', marginRight: 10, border: 'none', fontSize: 12 };

            return <div key={index} style={{ float: 'left', cursor: 'pointer' }}><span style={{ marginRight: 5, marginLeft: 5 }}>|</span><span type={"light"} style={style} onClick={this.changeNameSpace.bind(this, obj.namespace, obj.namespaceShowName)} key={index}>{obj.namespaceShowName}</span></div>;
        });
        return <div style={{ paddingTop: 9 }}>{namespacesBtn}</div>;
    }
    render() {
        let namespaceList = this.state.namespaceList || [];
        let title = this.props.title || '';
        const noticeStyle = {
            height: 45,
            lineHeight: '45px',
            backgroundColor: 'rgb(242, 242, 242)',
            border: '1px solid rgb(228, 228, 228)',
            padding: '0 20px',
            marginBottom: 5
        };
        return <div>
            {}
            {title ? <p style={{ height: 30, lineHeight: '30px', paddingTop: 0, paddingBottom: 0, borderLeft: '2px solid #09c', float: 'left', margin: 0, paddingLeft: 10 }}>{this.props.title}</p> : ''}
            <div style={{ float: 'left' }}>
                {this.rendernamespace(namespaceList)}
            </div>
            {/**
                      <div style={{ color: '#00C1DE', float: 'left', height: '32px', lineHeight: '32px', paddingRight: 10 }}>
                         Namespace: {this.state.nownamespace}
                      </div>**/}
            <div style={{ float: 'left' }} hidden={window._getLink && !window._getLink("dingding") && !window._getLink("discuz") && !window._getLink("learningPath")}>
                {!window.globalConfig.isParentEdas() ? <div style={{ float: 'left', height: '32px', lineHeight: '32px' }}>{aliwareIntl.get('com.alibaba.nacos.component.NameSpaceList.online_customer_support')}<a href={window._getLink && window._getLink("dingding")} hidden={window._getLink && !window._getLink("dingding")} className={"dingding"}></a>
                    <a href={window._getLink && window._getLink("discuz")} hidden={window._getLink && !window._getLink("discuz")} target={"_blank"} style={{ marginLeft: 10 }}>{aliwareIntl.get('nacos.component.NameSpaceList.Forum')}</a>
                    <span style={{ marginRight: 5, marginLeft: 5 }}>|</span>
                    <a href={window._getLink && window._getLink("learningPath")} hidden={window._getLink && !window._getLink("learningPath")} target={"_blank"}>{aliwareIntl.get('nacos.component.NameSpaceList.Quick_to_learn0') /*快速学习*/}</a>

                </div> : ''}
            </div>
            
        </div>;
    }
}
/*****************************此行为标记行, 请勿删和修改此行, 主体代码请写在此行上面的class中, 组件导出语句及其他信息请写在此行下面*****************************/
export default NameSpaceList;