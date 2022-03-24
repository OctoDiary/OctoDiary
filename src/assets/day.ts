import { Lesson } from "./lesson";

export interface Day {
  date: string,
  dayHomeworksProgress: {
    completedLessonsWithHomeworksCount: number,
    totalLessonsWithHomeworksCount: number,
  },
  hasImportantWork: boolean,
  lessons: Lesson[],
  messengerEntryPoint: any
}
