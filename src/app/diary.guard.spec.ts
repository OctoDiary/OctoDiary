import { TestBed } from '@angular/core/testing';

import { DiaryGuard } from './diary.guard';

describe('DiaryGuard', () => {
  let guard: DiaryGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(DiaryGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
