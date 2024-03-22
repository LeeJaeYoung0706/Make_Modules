# ReactQuery Hook을 사용하기 위한 설명서를 만들었습니다.

## useSelectQuery
단순하게 CRUD 중에 R 만을 담당합니다. api 요청을 해서 조회할 때 사용하는 HooK
```ts
const query = useSelectQuery(test , () => apiRequest.get(api))
const query1 = useSelectQuery(test , () => apiRequest.get(api) , !!mutation.error)
```
매개변수 1 : test -> QueryKey 생성하기
    const test = queryKey('test').addKey(testState , testState);
    const test1 = queryKey('test').defaultKey;
addKey : 변경되는 사유가 되는 변수 및 매개 변수를 포함시켜줄 때
defaultKey : 키 값만 지정하여 단순하게 불러올 때
매개변수 'test' : string key 값으로 고유하게 생성해주어야합니다.
매개변수 2 : () => apiRequest.get , post 사용할 수 있습니다
```ts
 const api = new APIConfig.Builder()
    .setUrl("/test")
    .setParam({test: "rororo"})
    .setMultipartUse(false)
    .build();
```
api 요청

url : 요청할 url 을 작성합니다. 필수 값입니다.

Param : 기본 값 null 로 보내지 않을 경우 set 함수를 호출하지 않아도 됩니다.

MultipartUse : 파일을 보낼때 true 로 보내줍니다. 사용하지 않을 경우 기본으로 false 가 적용되어 있습니다.

매개변수 3 : !!mutation.error

직렬로 API 요청을 해야할 경우 순서대로 반환된 값이 생성되었을 때 처리하도록 합니다. 한 개만 호출 할 경우에는 생략가능합니다.

코드
```ts
return useQuery(queryKey, callBack, {
    onError: (error?: unknown) => onErrorFun(error, dispatch, t), // error 발생 할 경우 타는 function
    select: (data: any) => data?.data?.data, // data result 변환처리
    retry: false, // 실패시 재 요청 횟수
    enabled: enabled !== undefined ? enabled : true
});
```
onError : api 에서 HTTP request 상태 값이 200이 아닐 경우 처리하는 Function 입니다.

select : api 에서 onSuccess 됐을 때 데이터를 변환해주는 역할을 합니다.

retry : 실패했을 경우 재 요청하는 횟수를 지정할 수 있습니다.

enabled : 직렬로 api 요청을 처리해야할 경우 변수 값을 넣어주고

true : 실행

false : 실행하지 않음

useUpdateQuery
CRUD 중에 수정 , 삭제 , 생성을 할 때 실행합니다.
```ts
 return useMutation(callBack, {
   onError: (error?: unknown) => onErrorFun(error, dispatch, t),  // error 발생 시 처리할 function
   onSuccess: () => {  // 성공 후 function
      queryKey !== undefined && queryClient.invalidateQueries(queryKey); // mutation을 성공하면 querykey 에 있는 useQuery 를 재 호출 합니다.
   },
   retry: false, // 실패시 재 요청 횟수
  });
onError : api 에서 HTTP request 상태 값이 200이 아닐 경우 처리하는 Function 입니다.

onSuccess: 요청에 성공했을 경우 실행 됩니다. invalidateQueries(queryKey) 에 useSelectQuery 로 넣은 Key 를 넣으면 useQuery 가 stale 상태가 되고 다시 요청하여 반환 값을 리렌더링 합니다.
```
사용법
const mutation = useMutation((variable: any) => apiRequest.patch(variable))
Component 에 선언해줍니다.
매개변수 1 : variable: any 이 곳에 값은 mutate(api) 에 들어갈 api 를 넣어줍니다. 이 때 api 의 타입을 APIProps 로 지정하여 처리하여도 됩니다. 저 매개 변수가 mutate 를 실행할 때 존재하는 매개변수 인
 callBack: (value: any) => Promise<AxiosResponse<any, any>>
api -> value 에 넣어져서 요청이 보내집니다. onClick 이벤트로 넣어서 수정처리나 삭제처리 등록처리를 할 경우에
```ts
  <button
      onClick={() => mutation.mutate(api , {
          onError: (data) => {
              console.log(data)},
          onSuccess : (data) => {
              // 이 곳에 성공 후 처리 
          }
      })
  }
  > 테스트 버튼 </button>
```
이렇게 mutaion.mutate() 함수를 호출하여 업데이트를 진행합니다.
