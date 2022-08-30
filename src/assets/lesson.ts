import { Work } from './work';

export interface Lesson {
  comment: any;
  endDateTime: string;
  group: {
    id: bigint;
    name: string;
    parentId: any;
    parentName: string;
  };
  hasAttachment: boolean;
  homework?: {
    attachments: any[];
    isCompleted: boolean;
    text: string;
    workIsAttachRequired: false;
  };
  id: bigint;
  importantWorks: string[];
  isCanceled: boolean;
  isEmpty: any;
  number: number;
  place: any;
  startDateTime: string;
  subject: {
    id: bigint;
    knowledgeArea: string;
    name: string;
    subjectMood: any;
  };
  teacher: {
    avatarUrl: string;
    firstName: string;
    lastName: string;
    middleName: string;
    personId: number;
  };
  theme: string;
  workMarks: Work[];
}
