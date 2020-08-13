/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

const I18N_CONF = {
  Header: {
    home: '首页',
    docs: '文档',
    blog: '博客',
    community: '社区',
    languageSwitchButton: 'En',
    logout: '登出',
    changePassword: '修改密码',
  },
  Login: {
    login: '登录',
    submit: '提交',
    pleaseInputUsername: '请输入用户名',
    pleaseInputPassword: '请输入密码',
    invalidUsernameOrPassword: '用户名或密码错误',
    passwordRequired: '密码不能为空',
    usernameRequired: '用户名不能为空',
  },
  MainLayout: {
    nacosName: 'NACOS',
    doesNotExist: '您访问的页面不存在',
    configurationManagementVirtual: '配置管理',
    configurationManagement: '配置列表',
    configdetail: '配置详情',
    configsync: '同步配置',
    configeditor: '配置编辑',
    newconfig: '新建配置',
    historyRollback: '历史版本',
    configRollback: '配置回滚',
    historyDetail: '历史详情',
    listeningToQuery: '监听查询',
    serviceManagementVirtual: '服务管理',
    serviceManagement: '服务列表',
    subscriberList: '订阅者列表',
    serviceDetail: '服务详情',
    namespace: '命名空间',
    clusterManagementVirtual: '集群管理',
    clusterManagement: '节点列表',
    authorityControl: '权限控制',
    userList: '用户列表',
    roleManagement: '角色管理',
    privilegeManagement: '权限管理',
  },
  Password: {
    passwordNotConsistent: '两次输入密码不一致',
    passwordRequired: '密码不能为空',
    pleaseInputOldPassword: '请输入原始密码',
    pleaseInputNewPassword: '请输入新密码',
    pleaseInputNewPasswordAgain: '请再次输入新密码',
    oldPassword: '原始密码',
    newPassword: '新密码',
    checkPassword: '再次输入',
    changePassword: '修改密码',
    invalidPassword: '原始密码错误',
    modifyPasswordFailed: '修改密码失败',
  },
  NameSpace: {
    namespace: '命名空间',
    prompt: '提示',
    namespaceDetails: '命名空间详情',
    namespaceName: '命名空间名称',
    namespaceID: '命名空间ID',
    configuration: '配置数',
    description: '描述',
    removeNamespace: '删除命名空间',
    confirmDelete: '确定要删除以下命名空间吗？',
    configurationManagement: '配置列表',
    removeSuccess: '删除命名空间成功',
    deletedSuccessfully: '删除成功',
    deletedFailure: '删除失败',
    namespaceDelete: '删除',
    details: '详情',
    edit: '编辑',
    namespacePublic: 'public(保留空间)',
    pubNoData: '没有数据',
    namespaceAdd: '新建命名空间',
    namespaceNames: '命名空间名称',
    namespaceNumber: '命名空间ID',
    namespaceOperation: '操作',
    refresh: '刷新',
  },
  ServiceList: {
    serviceList: '服务列表',
    serviceName: '服务名称',
    serviceNamePlaceholder: '请输入服务名称',
    hiddenEmptyService: '隐藏空服务',
    query: '查询',
    pubNoData: '没有数据',
    columnServiceName: '服务名',
    groupName: '分组名称',
    groupNamePlaceholder: '请输入分组名称',
    columnClusterCount: '集群数目',
    columnIpCount: '实例数',
    columnHealthyInstanceCount: '健康实例数',
    columnTriggerFlag: '触发保护阈值',
    operation: '操作',
    detail: '详情',
    sampleCode: '示例代码',
    deleteAction: '删除',
    prompt: '提示',
    promptDelete: '确定要删除当前服务吗？',
    create: '创建服务',
  },
  SubscriberList: {
    subscriberList: '订阅者列表',
    serviceName: '服务名称',
    serviceNamePlaceholder: '请输入服务名称',
    groupName: '分组名称',
    groupNamePlaceholder: '请输入分组名称',
    query: '查询',
    pubNoData: '没有数据',
    address: '地址',
    clientVersion: '客户端版本',
    appName: '应用名',
    searchServiceNamePrompt: '请输入服务名称！',
  },
  ClusterNodeList: {
    clusterNodeList: '节点列表',
    nodeIp: '节点Ip',
    nodeIpPlaceholder: '请输入节点Ip',
    query: '查询',
    pubNoData: '没有数据',
    nodeState: '节点状态',
    extendInfo: '节点元数据',
  },
  EditClusterDialog: {
    updateCluster: '更新集群',
    checkType: '检查类型',
    checkPort: '检查端口',
    useIpPortCheck: '使用IP端口检查',
    checkPath: '检查路径',
    checkHeaders: '检查头',
    metadata: '元数据',
  },
  ServiceDetail: {
    serviceDetails: '服务详情',
    back: '返回',
    editCluster: '集群配置',
    cluster: '集群',
    metadata: '元数据',
    selector: '表达式',
    type: '服务路由类型',
    groupName: '分组',
    protectThreshold: '保护阈值',
    serviceName: '服务名',
    editService: '编辑服务',
  },
  EditServiceDialog: {
    createService: '创建服务',
    updateService: '更新服务',
    serviceName: '服务名',
    metadata: '元数据',
    groupName: '分组',
    type: '服务路由类型',
    typeLabel: '标签',
    typeNone: '默认',
    selector: '表达式',
    protectThreshold: '保护阈值',
    serviceNameRequired: '请输入服务名',
    protectThresholdRequired: '请输入保护阈值',
  },
  InstanceTable: {
    operation: '操作',
    port: '端口',
    weight: '权重',
    healthy: '健康状态',
    metadata: '元数据',
    editor: '编辑',
    offline: '下线',
    online: '上线',
    ephemeral: '临时实例',
  },
  EditInstanceDialog: {
    port: '端口',
    weight: '权重',
    metadata: '元数据',
    updateInstance: '编辑实例',
    whetherOnline: '是否上线',
  },
  ListeningToQuery: {
    success: '成功',
    failure: '失败',
    configuration: '配置',
    pubNoData: '没有数据',
    listenerQuery: '监听查询',
    queryDimension: '查询维度',
    pleaseEnterTheDataId: '请输入Data ID',
    dataIdCanNotBeEmpty: 'Data ID不能为空',
    pleaseInputGroup: '请输入Group',
    groupCanNotBeEmpty: 'Group不能为空',
    pleaseInputIp: '请输入IP',
    query: '查询',
    queryResultsQuery: '查询结果：共查询到',
    articleMeetRequirementsConfiguration: '条满足要求的配置。',
  },
  HistoryRollback: {
    details: '详情',
    rollback: '回滚',
    pubNoData: '没有数据',
    toConfigure: '历史版本(保留30天)',
    dataId: '请输入Data ID',
    dataIdCanNotBeEmpty: 'Data ID不能为空',
    group: '请输入Group',
    groupCanNotBeEmpty: 'Group不能为空',
    query: '查询',
    queryResult: '查询结果：共查询到',
    articleMeet: '条满足要求的配置。',
    lastUpdateTime: '最后更新时间',
    operator: '操作人',
    operation: '操作',
  },
  HistoryDetail: {
    historyDetails: '历史详情',
    update: '更新',
    insert: '插入',
    deleteAction: '删除',
    recipientFrom: '收起',
    moreAdvancedOptions: '更多高级选项',
    home: '归属应用:',
    actionType: '操作类型:',
    configureContent: '配置内容:',
    operator: '操作人:',
    back: '返回',
  },
  DashboardCard: {
    importantReminder0: '重要提醒',
    viewDetails1: '查看详情',
  },
  ConfigurationManagement: {
    questionnaire2: '问卷调查',
    ad: '答 ACM 前端监控调查问卷，限时领取阿里云代金券\t            详情猛戳：',
    noLongerDisplay4: '不再显示：',
    removeConfiguration: '删除配置',
    sureDelete: '确定要删除以下配置吗？',
    environment: '地域：',
    configurationManagement: '配置列表',
    details: '详情',
    sampleCode: '示例代码',
    edit: '编辑',
    deleteAction: '删除',
    more: '更多',
    version: '历史版本',
    listenerQuery: '监听查询',
    failedEntry: '失败的条目:',
    successfulEntry: '成功的条目:',
    unprocessedEntry: '未处理的条目:',
    pubNoData: '没有数据',
    configurationManagement8: '配置管理',
    queryResults: '查询结果：共查询到',
    articleMeetRequirements: '条满足要求的配置。',
    fuzzyd: '模糊查询请输入Data ID',
    fuzzyg: '模糊查询请输入Group',
    query: '查询',
    advancedQuery9: '高级查询',
    application0: '归属应用:',
    app1: '请输入应用名',
    tags: '标签:',
    pleaseEnterTag: '请输入标签',
    application: '归属应用:',
    operation: '操作',
    export: '导出查询结果',
    import: '导入配置',
    uploadBtn: '上传文件',
    importSucc: '导入成功',
    importAbort: '导入终止',
    importSuccBegin: '导入成功,导入了',
    importSuccEnd: '项配置',
    importFail: '导入失败',
    importFail403: '没有权限!',
    importDataValidationError: '未读取到合法数据，请检查导入的数据文件。',
    metadataIllegal: '导入的元数据文件非法',
    namespaceNotExist: 'namespace 不存在',
    abortImport: '终止导入',
    skipImport: '跳过',
    overwriteImport: '覆盖',
    importRemind: '文件上传后将直接导入配置，请务必谨慎操作！',
    samePreparation: '相同配置',
    targetNamespace: '目标空间',
    conflictConfig: '检测到冲突的配置项',
    failureEntries: '失败的条目',
    unprocessedEntries: '未处理的条目',
    skippedEntries: '跳过的条目',
    exportSelected: '导出选中的配置',
    clone: '克隆',
    exportSelectedAlertTitle: '配置导出',
    exportSelectedAlertContent: '请选择要导出的配置',
    cloneSucc: '克隆成功',
    cloneAbort: '克隆终止',
    cloneSuccBegin: '克隆成功,克隆了',
    cloneSuccEnd: '项配置',
    cloneFail: '克隆失败',
    getNamespaceFailed: '获取命名空间失败',
    getNamespace403: '没有 ${tenant} 命名空间的访问权限！',
    startCloning: '开始克隆',
    cloningConfiguration: '克隆配置',
    source: '源空间：',
    configurationNumber: '配置数量：',
    target: '目标空间：',
    selectNamespace: '请选择命名空间',
    selectedEntry: '| 选中的条目',
    cloneSelectedAlertTitle: '配置克隆',
    cloneSelectedAlertContent: '请选择要克隆的配置',
    delSelectedAlertTitle: '配置删除',
    delSelectedAlertContent: '请选择要删除的配置',
    delSuccessMsg: '删除成功',
    cloneEditableTitle: '修改 Data Id 和 Group (可选操作)',
    authFail: '权限认证失败',
  },
  NewConfig: {
    newListingMain: '新建配置',
    newListing: '新建配置',
    publishFailed: '发布失败。请检查参数是否正确。',
    doNotEnter: '不允许非法字符',
    newConfig: 'Data ID 不能为空',
    dataIdIsNotEmpty: 'Data ID 长度不能超过255字符',
    groupPlaceholder: '请输入Group名称',
    moreAdvanced: 'Group不能为空',
    groupNotEmpty: 'Group ID长度不能超过127字符',
    annotation:
      '注：您正在往一个自定义分组新增配置，请确保客户端使用的Pandora版本高于3.4.0，否则可能读取不到该配置。',
    dataIdLength: '收起',
    collapse: '更多高级选项',
    tags: '标签:',
    pleaseEnterTag: '请输入标签',
    groupIdCannotBeLonger: '归属应用:',
    description: '描述:',
    targetEnvironment: '配置格式:',
    configurationFormat: '配置内容:',
    configureContentsOf: '按F1显示全屏',
    fullScreen: '按Esc退出全屏',
    escExit: '发布',
    release: '返回',
    confirmSyanx: '配置信息可能有语法错误, 确定提交吗?',
    dataIdExists: '配置已存在, 试试别的dataid和group的组合吧',
    dataRequired: '数据不能为空, 提交失败',
  },
  CloneDialog: {
    terminate: '终止克隆',
    skip: '跳过',
    cover: '覆盖',
    getNamespaceFailed: '获取命名空间失败',
    selectedEntry: '| 选中的条目',
    homeApplication: '归属应用:',
    tags: '标签:',
    startCloning: '开始克隆',
    source: '源空间：',
    configurationNumber: '配置数量：',
    target: '目标空间：',
    conflict: '相同配置:',
    selectNamespace: '请选择命名空间',
    configurationCloning: '配置克隆（',
  },
  DeleteDialog: {
    confManagement: '配置管理',
    determine: '确定',
    deletetitle: '删除配置',
    deletedSuccessfully: '删除配置成功',
    deleteFailed: '删除配置失败',
  },
  DiffEditorDialog: {
    publish: '确认发布',
    contents: '内容比较',
    currentArea: '当前值',
    originalValue: '原始值',
  },
  ConfigEditor: {
    official: '正式',
    production: '正式',
    beta: 'BETA',
    wrong: '错误',
    submitFailed: '不能为空, 提交失败',
    toedittitle: '编辑配置',
    toedit: '编辑配置',
    newConfigEditor: '新建配置',
    vdchart: '请勿输入非法字符',
    recipientFrom: 'Data ID不能为空',
    homeApplication: 'Group不能为空',
    collapse: '收起',
    groupNotEmpty: '更多高级选项',
    tags: '标签:',
    pleaseEnterTag: '请输入标签',
    targetEnvironment: '归属应用:',
    description: '描述:',
    format: '配置格式:',
    configcontent: '配置内容',
    escExit: '按F1显示全屏',
    releaseBeta: '按Esc退出全屏',
    release: '发布Beta',
    stopPublishBeta: '停止Beta',
    betaPublish: 'Beta发布:',
    betaSwitchPrompt: '默认不要勾选。',
    publish: '发布',
    back: '返回',
    codeValErrorPrompt: '配置信息可能有语法错误, 确定提交吗?',
  },
  EditorNameSpace: {
    notice: '提示',
    pleaseDo: '请勿输入非法字符',
    publicSpace: '确认修改',
    confirmModify: '编辑命名空间',
    editNamespace: '加载中...',
    load: '命名空间名：',
    namespace: '命名空间不能为空',
    namespaceDesc: '命名空间描述不能为空',
    description: '描述：',
  },
  ExportDialog: {
    selectedEntry: '| 选中的条目',
    application: '归属应用:',
    tags: '标签:',
    exportBtn: '导出',
    exportConfiguration: '导出配置（',
    source: '源空间：',
    items: '配置数量：',
  },
  ImportDialog: {
    terminate: '终止导入',
    skip: '跳过',
    overwrite: '覆盖',
    zipFileFormat: '只能上传.zip格式的文件',
    uploadFile: '上传文件',
    importLabel: '导入配置 ( ',
    target: '目标空间：',
    conflict: '相同配置：',
    beSureExerciseCaution: '文件上传后将直接导入配置，请务必谨慎操作',
  },
  ShowCodeing: {
    sampleCode: '示例代码',
    loading: '加载中...',
  },
  SuccessDialog: {
    title: '配置管理',
    determine: '确定',
    failure: '失败',
  },
  ConfigSync: {
    error: '错误',
    syncConfigurationMain: '同步配置',
    syncConfiguration: '同步配置成功',
    advancedOptions: '更多高级选项',
    collapse: '收起',
    home: '归属应用：',
    region: '所属地域：',
    configuration: '配置内容：',
    target: '目标地域：',
    sync: '同步',
    back: '返回',
  },
  NewNameSpace: {
    norepeat: '命名空间名称不能重复',
    notice: '提示',
    input: '请勿输入非法字符',
    ok: '确定',
    cancel: '取消',
    newnamespce: '新建命名空间',
    loading: '加载中...',
    name: '命名空间名：',
    namespaceId: '命名空间ID(不填则自动生成)：',
    namespaceIdTooLong: '命名空间ID长度不能超过128',
    namespacenotnull: '命名空间不能为空',
    namespacedescnotnull: '命名空间描述不能为空',
    description: '描述：',
    namespaceIdAlreadyExist: 'namespaceId已存在',
    newnamespceFailedMessage: 'namespaceId格式不正确/namespaceId长度大于128/namespaceId已存在',
  },
  NameSpaceList: {
    notice: '提示',
  },
  ConfigDetail: {
    official: '正式',
    error: '错误',
    configurationDetails: '配置详情',
    collapse: '收起',
    more: '更多高级选项',
    home: '归属应用：',
    tags: '标签:',
    description: '描述：',
    betaRelease: 'Beta发布：',
    configuration: '配置内容：',
    back: '返回',
  },
  ConfigRollback: {
    rollBack: '回滚配置',
    determine: '确定要',
    followingConfiguration: '以下配置吗？',
    configurationRollback: '配置回滚',
    collapse: '收起',
    more: '更多高级选项',
    home: '归属应用：',
    actionType: '操作类型：',
    configuration: '配置内容：',
    back: '返回',
    rollbackSuccessful: '回滚成功',
    rollbackDelete: '删除',
    update: '更新',
    insert: '插入',
  },
  UserManagement: {
    userManagement: '用户管理',
    createUser: '创建用户',
    resetPassword: '修改',
    deleteUser: '删除',
    deleteUserTip: '是否要删除该用户？',
    username: '用户名',
    password: '密码',
    operation: '操作',
    refresh: '刷新',
  },
  NewUser: {
    createUser: '创建用户',
    username: '用户名',
    password: '密码',
    rePassword: '确认密码',
    usernamePlaceholder: '请输入用户名',
    passwordPlaceholder: '请输入密码',
    rePasswordPlaceholder: '请输入确认密码',
    usernameError: '用户名不能为空！',
    passwordError: '密码不能为空!',
    rePasswordError: '确认密码不能为空!',
    rePasswordError2: '两次输入密码不一致!',
  },
  PasswordReset: {
    resetPassword: '密码重置',
    username: '用户名',
    password: '密码',
    rePassword: '确认密码',
    passwordError: '密码不能为空！',
    passwordPlaceholder: '请输入密码',
    rePasswordPlaceholder: '请输入确认密码',
    rePasswordError: '确认密码不能为空!',
    rePasswordError2: '两次输入密码不一致!',
  },
  RolesManagement: {
    roleManagement: '角色管理',
    bindingRoles: '绑定角色',
    role: '角色名',
    username: '用户名',
    operation: '操作',
    deleteRole: '删除',
    deleteRoleTip: '是否要删除该角色？',
    refresh: '刷新',
  },
  NewRole: {
    bindingRoles: '绑定角色',
    username: '用户名',
    role: '角色名',
    usernamePlaceholder: '请输入用户名',
    rolePlaceholder: '请输入角色名',
    usernameError: '用户名不能为空！',
    roleError: '角色名不能为空!',
  },
  PermissionsManagement: {
    privilegeManagement: '权限管理',
    addPermission: '添加权限',
    role: '角色名',
    resource: '资源',
    action: '动作',
    operation: '操作',
    deletePermission: '删除',
    deletePermissionTip: '是否要删除该权限？',
    readOnly: '只读',
    writeOnly: '只写',
    readWrite: '读写',
    refresh: '刷新',
  },
  NewPermissions: {
    addPermission: '添加权限',
    role: '角色名',
    resource: '资源',
    action: '动作',
    resourcePlaceholder: '请选择资源',
    rolePlaceholder: '请输入角色名',
    actionPlaceholder: '请选择动作',
    resourceError: '资源不能为空！',
    roleError: '角色名不能为空!',
    actionError: '动作不能为空!',
    readOnly: '只读',
    writeOnly: '只写',
    readWrite: '读写',
  },
};

export default I18N_CONF;
