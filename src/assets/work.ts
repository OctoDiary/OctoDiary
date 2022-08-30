import { Mark } from './mark';

export interface Work {
  workId: bigint;
  marks: Mark[];
}
