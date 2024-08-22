## Early return

> else 사용을 '지양'하자. switch도 유사하다. 사용하지 말자가 아니다.

```typescript
if (password.length >= 8) {
  doSomeThing1();
} else if (password <= 3 && user === 'ADMIN') {
  doSomeThing2();
} else {
  doSomeThing3();
}
```

> 비밀번호는 8자가 넘어야해. 넘으면 일해. 아 참, 그런데 3글자가 넘지 않고 사용자가 관리자면 다른 일해. 또는 이거해.

Early return을 적용하기 전 작성한 코드는 위와 같다. 모든 조건을 인지하고 있어야해서 이해하기 어렵다.
RAM은 한정적인데 과하게 사용한다. 그렇다면 오늘의 주인공 Early return을 적용한 구조는 어떨까.

```typescript
if (password.length >= 8) {
  doSomeThing1();
  return;
}

if (password <= 3 && user === 'ADMIN') {
  doSomeThing2();
  return;
}
doSomeThing3();
```

글쓰기를 좋아한다. 글을 작성할 때는 일단 쓰고 필요없는 단어를 지우며 퇴고한다. 프로그래밍 언어에 상관없이 개발의 조건을 작성할 때 `if...else`는 한번 쯤 배우고 넘어간다.
`return` 키워드를 추가해서 분리한다. 다음은 즐겨보는 mdn web docs에서 소개하는 예제를 각색했다.

```typescript
function isPositive(num: number) {
  if (num > 0) {
    return true;
  } else {
    return false;
  }
}
```

처음 개발을 배울때 작성하는 익숙한 코드다. 함수 isPositive는 입력받은 num 변수가 양수인지, 음수인지 확인하는 함수다. 작성한 코드를 한국어로 풀어쓰면 다음과 같다.
매개변수 num을 가지는 isPositive를 선언한다. num이 0보다 크면 POSITIVE(양수)를 반환하고 또는 NOT POSITIVE를 반환한다.
간단한 프로그램이어서 무난하게 읽힌다고 생각할 수 있다. 그런데 내가 원하는 문장이 아니다. 퇴고를 진행해보자.

```typescript
function isPositive(num: number) {
  if (num < 0) {
    return false;
  }

  return true;
}
```

같은 동작을 하는 코드를 수정했다. 수정한 함수는 다음과 같이 읽힌다.

> 매개변수 num을 가지는 isPositive를 선언한다. (동일) num이 0보다 작으면 false 를 반환한다. true를 반환한다.

크게 2가지를 수정했다. 로직의 순서와 또는과 같은 접속사를 버렸다. 내 생각에 isPositive를 읽는 사람은 이름을 보고 양수인지 확인하는 프로그램이라고 생각할 것 같다.
이 때 중요한건 '양수가 아닌 케이스를 어떻게 처리할지 여부'라고 생각했다. 그래서 예외처리 하는 부분을 위로 올렸다.

이전 코드는 '조건을 만족하면 true 반환하고 만족하지 않으면 false를 반환한다'는 식으로 작성했다.
하지만, Early return을 사용해서 '조건이 아니면 false를 반환한다'라는 예외케이스를 우선 배치했다.
그리고 위와 같은 조건은 `else`가 없어도 큰 문제가 없다고 생각한다. 오히려 필요없는 단어를 사용해서 지웠다.

**<참고 자료>**

- [『프로그래머의 뇌』(펠리너 헤르만스, 제이펍, 2022)](https://product.kyobobook.co.kr/detail/S000001952236)
- [『리팩터링』(마틴 파울러, 한빛미디어, 2020)](https://product.kyobobook.co.kr/detail/S000001810241)

## 사고의 depth 줄이기

- 중첩 분기문, 중첩 반복문
- 사용할 변수는 가깝게 선언하기

'무조건 1 depth로 만들어라'가 아니다. 사고를 하는 RAM의 비용을 줄이자. 만약 중첩 구조가 사고에 도움이 된다면 그대로 두자. 중요한 점은 자연스러운 사고다.

### 중첩 분기문, 중첩 반복문

```java
private static boolean isAllCellOpened2() {
    return Arrays.stream(BOARD)// Stream<String[]>
            .flatMap(Arrays::stream) // Stream<String>
            .noneMatch(cell -> cell.equals(CLOSED_CELL_SIGN));
}
```

Stream을 사용한다. 만약 Stream 같은 문법이 읽기 어렵다면 '로직을 나눠고 변수를 추출'하는 방법을 추천한다.

### 사용할 변수는 가깝게 선언하기

> 원숭이 엉덩이는 빨개<br>
> 빨가면 사과<br>
> 바나나는 길어<br>
> 길으면 기차<br>
> 기차는 빨라<br>
> 빠르면 비행기<br>
> 비행기는 높아<br>
> 높으면 백두산<br>
> 아 맞다 사과는 맛있어 (?)

설명하는 문장을 떨어트리지 말고 이어서 작성한다.
