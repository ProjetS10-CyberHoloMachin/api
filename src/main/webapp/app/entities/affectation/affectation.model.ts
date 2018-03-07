import { BaseEntity, User } from './../../shared';

export class Affectation implements BaseEntity {
    constructor(
        public id?: number,
        public user?: User,
        public notification?: BaseEntity,
    ) {
    }
}
