import { BaseEntity } from './../../shared';

export class InfoDefinition implements BaseEntity {
    constructor(
        public id?: number,
        public label?: string,
    ) {
    }
}
