create table appointment
(
    id                      int auto_increment comment '预约表id'
        primary key,
    user_id                 int                                not null comment '用户Id',
    counselor_id            int                                not null comment '咨询师id',
    area_id                 int                                null comment '预约领域id',
    appointment_date        date                               null comment '预约时间例如 2024:04:28',
    appointment_time        time                               null comment '预约时间段',
    appointment_description varchar(255)                       not null comment '预约的问题描述',
    appointment_reply       varchar(255)                       null comment '预约回复',
    status                  int                                null comment '1已申请 2预约成功 3预约失败',
    price                   double                             null comment '预约价格',
    create_time             datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time             datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete               int      default 0                 null comment '0未删除 1已删除',
    constraint appointment1_id_uindex
        unique (id)
)
    comment '预约表';

create table area
(
    id        int auto_increment comment '领域表Id'
        primary key,
    area_name varchar(255) null comment '领域名称',
    constraint area_id_uindex
        unique (id)
)
    comment '领域表';

create table comment
(
    id              int auto_increment comment '评论表id'
        primary key,
    user_id         int                                not null comment '用户Id',
    counselor_id    int                                not null comment '咨询师id',
    comment_content varchar(255)                       null comment '评论的内容',
    appointment_id  int                                not null comment '预约id',
    create_time     datetime default CURRENT_TIMESTAMP null comment '创建时间',
    is_delete       int      default 0                 null comment '0未删除 1已删除',
    constraint comment_id_uindex
        unique (id)
)
    comment '评论表';

create table counselor
(
    id             int auto_increment comment '咨询师表id'
        primary key,
    counselor_name varchar(255)                       null comment '咨询师姓名',
    admin_id       int                                null comment '管理员id',
    school         varchar(255)                       null comment '毕业院校',
    educationLv    varchar(255)                       not null comment '学历',
    Introduction   varchar(255)                       null comment '个人简介',
    background     varchar(255)                       null comment '专业背景',
    create_time    datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time    datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete      int                                null comment '0未删除 1删除',
    constraint counselor_id_uindex
        unique (id)
)
    comment '咨询师表';

create table diagnosis
(
    id                    int                                null comment '诊断表id',
    user_id               int                                null comment '用户id',
    appointment_id        int                                not null comment '预约Id',
    diagnosis_results     varchar(255)                       null comment '诊断结果',
    diagnosis_description varchar(255)                       null comment '诊断详细描述',
    create_time           datetime default CURRENT_TIMESTAMP null comment '诊断时间',
    is_delete             int                                null comment '0未删除 1已删除'
)
    comment '诊断表';

create table question_bank
(
    id          int auto_increment comment '题库表id'
        primary key,
    area_id     int                                null comment '领域id',
    question    varchar(255)                       null comment '题目json字符串',
    result      varchar(255)                       null comment '结果',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    is_detele   int      default 1                 null comment '0未删除 1已删除',
    constraint question_bank_id_uindex
        unique (id)
)
    comment '题库表';

create table question_bank_report
(
    id          int auto_increment comment '题库报告表id'
        primary key,
    user_id     int                                null comment '用户id',
    area_id     int                                null comment '领域id',
    result      varchar(255)                       null comment '做题实际结果',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    is_detele   int      default 0                 null comment '0未删除 1删除',
    constraint question_bank_report_id_uindex
        unique (id)
)
    comment '题库报告表';

create table sys_admin
(
    sys_admin_id  int auto_increment comment '管理员表Id'
        primary key,
    sys_account   varchar(255)                       not null comment '系统管理员账户',
    sys_password  varchar(255)                       not null comment '系统管理员密码',
    admin_name    varchar(255)                       null comment '管理员姓名',
    admin_role_id int                                not null comment '由后台管理员分配的权限',
    status        int      default 1                 null comment '0 禁用 1启用',
    phone         varchar(255)                       null comment '手机号',
    avatar        varchar(255)                       null comment '头像',
    create_time   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete     int      default 0                 null comment '0未删除 1删除',
    constraint sys_admin_sys_account_uindex
        unique (sys_account),
    constraint sys_admin_sys_admin_id_uindex
        unique (sys_admin_id)
)
    comment '系统后台';

create table sys_admin_counselor
(
    id           int auto_increment comment '系统管理员和咨询师对应表的id'
        primary key,
    counselor_id int    not null comment '咨询师对应Id',
    area_id      int    not null comment '领域Id',
    price        double null comment '某个咨询师在某个领域的价格',
    constraint sys_admin_counselor_id_uindex
        unique (id)
)
    comment '系统管理员和咨询师对应表';

create table sys_dict
(
    id        int auto_increment comment '字典参数表的id'
        primary key,
    dict_name varchar(255) null comment '字典参数名',
    dict_data varchar(255) null comment '字典参数数据'
)
    comment '字典参数表';

create table sys_log
(
    id            int                                 null comment '日志id',
    admin_id      int                                 null comment '管理员id',
    log_content   varchar(255)                        not null comment '操作内容',
    log_result    varchar(255)                        null comment '操作结果',
    input_params  varchar(255)                        not null comment '入参字符串',
    output_params varchar(255)                        not null comment '出参 json字符串',
    create_time   timestamp default CURRENT_TIMESTAMP null comment '日志发生事件，用时间戳便于排序',
    is_delete     int       default 0                 null comment '0未删除 1删除'
)
    comment '系统日志表';

create table sys_menu
(
    sys_menu_id    int auto_increment comment '系统菜单Id'
        primary key,
    menu_name      varchar(255)                       null comment '系统菜单名',
    menu_component varchar(255)                       null comment '系统菜单组件',
    menu_path      varchar(255)                       null comment '系统菜单路径',
    menu_icon      varchar(255)                       null comment '菜单图标',
    menu_status    int      default 1                 null comment '菜单是否启用1启用0禁用',
    menu_parent_id int      default 0                 null comment '所属父级菜单id',
    menu_is_hidden int      default 0                 null comment '菜单是否隐藏1隐藏0未隐藏',
    menu_sort      int      default 0                 null comment '菜单排序-数字越大排在越上面',
    create_time    datetime default CURRENT_TIMESTAMP null comment '菜单创建时间',
    update_time    datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete      int      default 0                 null comment '是否逻辑删除 0未删除 1删除',
    constraint sys_menu_menu_component_uindex
        unique (menu_component),
    constraint sys_menu_menu_name_uindex
        unique (menu_name),
    constraint sys_menu_sys_menu_id_uindex
        unique (sys_menu_id)
)
    comment '系统菜单表';

create table sys_role
(
    id            int          null comment '系统角色表id',
    sys_role_id   int auto_increment comment '系统角色id'
        primary key,
    sys_role_name varchar(255) not null comment '系统角色名',
    constraint sys_role_sys_role_id_uindex
        unique (sys_role_id),
    constraint sys_role_sys_role_name_uindex
        unique (sys_role_name)
)
    comment '系统角色表';

create table sys_role_menu
(
    id      int auto_increment comment '系统角色菜单关联表id'
        primary key,
    role_id int not null comment '角色Id',
    menu_id int not null comment '菜单Id',
    constraint sys_role_menu_id_uindex
        unique (id)
)
    comment '系统角色菜单关联表';

create table sys_user
(
    user_id       int auto_increment comment '系统用户表id'
        primary key,
    user_account  varchar(255)                       not null comment '系统用户账户',
    user_password varchar(255)                       not null comment '系统用户密码',
    user_name     varchar(255)                       not null comment '用户名',
    sex           int                                not null comment '0男 1女',
    age           int                                not null comment '年龄',
    address       varchar(255)                       null comment '通讯地址',
    phone         int                                not null comment '手机号',
    staus         tinyint  default 0                 null comment '0启用 1禁用',
    money         double   default 0                 null comment '余额',
    avatar        varchar(255)                       null comment '头像',
    create_time   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    is_delete     int      default 0                 null comment '逻辑删除 0不删除 1删除',
    constraint sys_user_user_account_uindex
        unique (user_account),
    constraint sys_user_user_id_uindex
        unique (user_id)
)
    comment '系统用户表';

create table transaction_counselor
(
    id                 int auto_increment comment '账单流水Id'
        primary key,
    transaction_id     varchar(255)                       null comment '流水号',
    counselor_id       int                                not null comment '咨询师Id',
    transaction_type   int                                null comment '流水类型 1充值 2消费',
    money              double                             null comment '交易金额',
    transaction_status int                                null comment '交易状态 1 成功 2失败 3进行中',
    pay_channel        int                                null comment '交易方式 1支付宝 2微信',
    transaction_info   varchar(255)                       null comment '交易附加信息',
    create_time        datetime default CURRENT_TIMESTAMP null comment '交易时间',
    is_delete          int                                null comment '0未删除 1已删除'
)
    comment '账单流水表';

create table transaction_user
(
    id                 int auto_increment comment '账单流水Id'
        primary key,
    transaction_id     varchar(255)                       null comment '流水号',
    user_id            int                                not null comment '用户Id',
    transaction_type   int                                null comment '流水类型 1充值 2消费',
    money              double                             null comment '交易金额',
    transaction_status int                                null comment '交易状态 1 成功 2失败 3进行中',
    pay_channel        int                                null comment '交易方式 1支付宝 2微信',
    transaction_info   varchar(255)                       null comment '交易附加信息',
    create_time        datetime default CURRENT_TIMESTAMP null comment '交易时间',
    is_delete          int                                null comment '0未删除 1已删除',
    constraint transaction_user_id_uindex
        unique (id)
)
    comment '账单流水表';

create table work_schedule
(
    id           int           null,
    counselor_id int           not null comment '咨询师id',
    date         date          not null comment '日期',
    time_slot    time          not null comment '排班的时间段字符串。例如''11:00 - 12:00''',
    status       int default 1 null comment '2预约 1空闲'
)
    comment '排班表';

