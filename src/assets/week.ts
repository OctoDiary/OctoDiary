import { Day } from "./day";

export interface Week {
  days: Day[],
  firstWeekDayDate: string,
  homeworksCount: string,
  id: string,
  lastWeekDayDate: string,
}
