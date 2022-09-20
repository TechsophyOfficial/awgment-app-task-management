package com.techsophy.awgment.taskmanagement.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TaskManagementConstants {

    //JWTRoleConverter
    public static final String CLIENT_ROLES="clientRoles";
    public static final String USER_INFO_URL= "techsophy-platform/protocol/openid-connect/userinfo";
    public static final String TOKEN_VERIFICATION_FAILED="Token verification failed";
    public static final String AWGMENT_ROLES_MISSING_IN_CLIENT_ROLES ="AwgmentRoles are missing in clientRoles";
    public static final String CLIENT_ROLES_MISSING_IN_USER_INFORMATION="ClientRoles are missing in the userInformation";
    public static final String ERROR= "error";


    /*LocaleConfig Constants*/
    public static final String ACCEPT_LANGUAGE = "Accept-Language";
    public static final String BASENAME_ERROR_MESSAGES = "classpath:errorMessages";
    public static final String BASENAME_MESSAGES = "classpath:messages";
    public static final Long CACHEMILLIS = 3600L;
    public static final Boolean USEDEFAULTCODEMESSAGE = true;

    // Roles
    public static final String HAS_ANY_AUTHORITY="hasAnyAuthority('";
    public static final String HAS_ANY_AUTHORITY_ENDING="')";
    public static final String AWGMENT_ACCOUNT_CREATE_OR_UPDATE = "awgment-account-create-or-update";
    public static final String AWGMENT_ACCOUNT_READ = "awgment-account-read";
    public static final String AWGMENT_ACCOUNT_DELETE = "awgment-account-delete";
    public static final String AWGMENT_ACCOUNT_ALL="awgment-account-all";
    public static final String OR=" or ";
    public static final String CREATE_OR_ALL_ACCESS =HAS_ANY_AUTHORITY+ AWGMENT_ACCOUNT_CREATE_OR_UPDATE +HAS_ANY_AUTHORITY_ENDING+OR+HAS_ANY_AUTHORITY+AWGMENT_ACCOUNT_ALL+HAS_ANY_AUTHORITY_ENDING;
    public static final String READ_OR_ALL_ACCESS =HAS_ANY_AUTHORITY+ AWGMENT_ACCOUNT_READ +HAS_ANY_AUTHORITY_ENDING+OR+HAS_ANY_AUTHORITY+AWGMENT_ACCOUNT_ALL+HAS_ANY_AUTHORITY_ENDING;
    public static final String DELETE_OR_ALL_ACCESS =HAS_ANY_AUTHORITY+AWGMENT_ACCOUNT_DELETE+HAS_ANY_AUTHORITY_ENDING+OR+HAS_ANY_AUTHORITY+AWGMENT_ACCOUNT_ALL+HAS_ANY_AUTHORITY_ENDING;

    /*TenantAuthenticationManagerConstants*/
    public static final String KEYCLOAK_ISSUER_URI = "${keycloak.issuer-uri}";
    public static final String DEFAULT_PAGE_LIMIT = "${default.pagelimit}";
    /*TokenConfigConstants*/
    public static final String AUTHORIZATION="Authorization";


    public static final String GATEWAY_URL ="${gateway.uri}";
    public static final String TASK_MANAGMENT_MODELER ="awgment-task-management";
    public static final String VERSION_1="v1";
    public static final String TASK_MANAGEMENT_API_VERSION_1="Task Management API v1";


    public static final String VERSION_V1="/v1";

    public static final String PAGE="page";
    public static final String SIZE="size";
    public static final String SORT_BY="sort-by";
    public static final String ID="id";






    public static final String FILTER_VALUE="filter-value";
    public static final String FILTER_COLUMN_NAME="filter-column";



    public static final String BASE_URL= "/task-management";










    public static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String TIME_ZONE = "UTC";





    public static final String GATEWAY_URI="${gateway.uri}";
    public static final String CONTENT_TYPE="Content-Type";
    public static final String APPLICATION_JSON="application/json";

    public static final String DATA="data";

    //ThemesCustomRepo
    public static final String UNDERSCORE_ID="_id";


    public static final String DESCENDING="desc";





    public static final String SPACE=" ";





    public static final String USER_DEFINITION_FIRST_NAME ="firstName";
    public static final String USER_DEFINITION_LAST_NAME ="lastName";


    public static final String COLLATION_EN="en";



    public static final String  URL_SEPERATOR="/";


    //TokenUtilsConstants
    public static final String BEARER= "Bearer ";
    public static final String REGEX_SPLIT="\\.";
    public static final String AUTHENTICATION_FAILED="Authentication failed";
    public static final String UNABLE_GET_TOKEN="Unable to get token";
    public static final String PREFERED_USERNAME="preferred_username";
    public static final String CREATED_ON="createdOn";
    public static final String  COLON=":";
    public static final String ISS="iss";

    //UserDetailsConstants
    public static final String TOKEN_NOT_NULL="token should not be null";
    public static final String ACCOUNT_URL = "/accounts/v1/users";
    public static final String FILTER_COLUMN="?filter-column=loginId&filter-value=";
    public static final String  ONLY_MANDATORY_FIELDS_TRUE="&only-mandatory-fields=true";

    //WebClientWrapperConstants
    public static final String GET="GET";
    public static final String PUT="PUT";
    public static final String DELETE="DELETE";
    public static final String POST="POST";
    public static final String BRIEF_REPRESENTATION="briefRepresentation";
    public static final String EXACT="exact";
    public static final String FIRST="first";
    public static final String MAX="max";
    public static final String SEARCH="search";


    /*MainMethodConstants*/
    public static final String CURRENT_PROJECT="com.techsophy.awgment.taskmanagement.*";
    public static final String MULTITENANCY_PROJECT="com.techsophy.multitenancy.mongo.*";
    public static final String EMPTY_STRING = "String Is Empty";
    public static final int SEVEN =7;
    public static final int ONE =1 ;


    //TASKConstants
    public static final String TASKS ="/tasks";


    public static final String SAVE_TASK_SUCCESS ="TASK.SAVED.SUCCESS";
    public static final String DELETE_TASK_SUCCESS ="TASK.DELETE.SUCCESS";
    public static final String GET_TASK_SUCCESS ="TASK.RETRIEVE.SUCCESS";
    public static final String UPDATE_TASK_SUCCESS ="TASK.UPDATE.SUCCESS";
    public static final String TASK_ID ="/{id}";


}
