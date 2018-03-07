import { BaseEntity } from './../../shared';

export class Building implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public data?: BaseEntity[],
        public notifications?: BaseEntity[],
    ) {
    }
}
