export function months(num: number): string {
  const months_arr =
    'января,февраля,марта,апреля,мая,июня,июля,августа,сентября,октября,ноября,декабря'.split(
      ','
    );
  return months_arr[num];
}
