interface RankingRecord {
  date: string;
  place: number;
}

export interface UserInfo {
  avatarUrl: string;
  classLetter: string;
  className: string;
  classNumber: string;
  firstName: string;
  groupId: number;
  lastName: string;
  middleName: string;
  personId: number;
  rankingHistory: RankingRecord[];
  rankingPlace: number;
  schoolAvatarUrl: string;
  schoolGeoPosition: any[];
  schoolId: number;
  schoolName: string;
  sex: string;
  userId: number;
}
