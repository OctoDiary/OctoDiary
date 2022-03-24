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
  lastName: string;
  middleName: string;
  rankingHistory: RankingRecord[];
  rankingPlace: number;
  schoolAvatarUrl: string;
  schoolGeoPosition: any[];
  schoolName: string;
  sex: string;
  userId: number;
}
