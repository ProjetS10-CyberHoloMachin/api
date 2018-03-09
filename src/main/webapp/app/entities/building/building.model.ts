import { BaseEntity } from './../../shared';

export class Building implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public mappingContentType?: string,
        public mapping?: any,
        public data?: BaseEntity[],
        public notifications?: BaseEntity[],
    ) {
    }
}
