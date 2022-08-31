import { AfterViewInit, Component, ElementRef, QueryList, ViewChildren } from '@angular/core';
import { DataService } from '../../data.service';
import { months } from '../../../assets/months';

@Component({
  selector: 'app-lessons',
  templateUrl: './lessons.component.html',
  styleUrls: ['./lessons.component.scss'],
})
export class LessonsComponent implements AfterViewInit {
  todayDate: Date = new Date();
  //today: string = `${("0" + (this.todayDate.getMonth() + 1)).slice(-2)}-${("0" + (this.todayDate.getDate())).slice(-2)}`;
  today: string = `05-20`;

  @ViewChildren('day', {read: ElementRef}) renderedDays!: QueryList<ElementRef>;

  constructor(public data: DataService) {
  }

  ngAfterViewInit() {
    this.renderedDays.changes.subscribe((newList: QueryList<ElementRef>) => {
      newList.forEach((elRef) => {
        console.log(1);
        if (elRef.nativeElement.id == this.today) elRef.nativeElement.scrollIntoView({behavior: 'smooth', block: 'center'})
      })
    })
  }

  getTimeRepresentation(
    d: string,
    type: 'full_date' | 'common_date' | 'only_time'
  ): string {
    const date = new Date(d);
    const zeroStart = (num: number) =>
      num.toString().length > 1 ? num.toString() : '0' + num;
    switch (type) {
      case 'common_date':
        return `${zeroStart(date.getUTCDate())} ${months(date.getUTCMonth())}`;
      case 'full_date':
        return `${zeroStart(date.getUTCDate())} ${months(
          date.getUTCMonth()
        )} ${date.getUTCFullYear()}`;
      case 'only_time':
        return `${date.getUTCHours()}:${zeroStart(date.getUTCMinutes())}`;
    }
  }

  getHomeworkRepresentation(h: string): string {
    const arr = h.split('; ');
    if (arr[0] === arr[1]) {
      return arr[0];
    } else {
      return arr.join(' | ');
    }
  }
}
