import { Component } from '@angular/core';
import { DataService } from "../../data.service";
import { months } from "../../../assets/months";

@Component({
  selector: 'app-lessons',
  templateUrl: './lessons.component.html',
  styleUrls: ['./lessons.component.scss']
})
export class LessonsComponent {

  today: Date = new Date();

  constructor(public data: DataService) {
    console.log(this.getMonday(new Date()));
  }

  getMonday(date: Date) {
    const d = new Date(date);
    const day = d.getDay();
    const diff = d.getDate() - day + (day == 0 ? -6 : 1);
    return new Date(d.setDate(diff));
  }

  getTimeRepresentation(d: string, type: 'full_date' | 'common_date' | 'only_time'): string {
    const date = new Date(d);
    const zeroStart = (num: number) => num.toString().length > 1 ? num.toString() : '0' + num;
    switch (type) {
      case "common_date":
        return `${zeroStart(date.getUTCDate())} ${months(date.getUTCMonth())}`;
      case "full_date":
        return `${zeroStart(date.getUTCDate())} ${months(date.getUTCMonth())} ${date.getUTCFullYear()}`;
      case "only_time":
        return `${date.getUTCHours()}:${zeroStart(date.getUTCMinutes())}`;
    }
  }

  getHomeworkRepresentation(h: string): string {
    const arr = h.split('; ');
    if (arr[0] === arr[1]) {
      return arr[0]
    } else {
      return arr.join(' | ')
    }
  }

}
