/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { MicroTestModule } from '../../../test.module';
import { InfoDefinitionDialogComponent } from '../../../../../../main/webapp/app/entities/info-definition/info-definition-dialog.component';
import { InfoDefinitionService } from '../../../../../../main/webapp/app/entities/info-definition/info-definition.service';
import { InfoDefinition } from '../../../../../../main/webapp/app/entities/info-definition/info-definition.model';

describe('Component Tests', () => {

    describe('InfoDefinition Management Dialog Component', () => {
        let comp: InfoDefinitionDialogComponent;
        let fixture: ComponentFixture<InfoDefinitionDialogComponent>;
        let service: InfoDefinitionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MicroTestModule],
                declarations: [InfoDefinitionDialogComponent],
                providers: [
                    InfoDefinitionService
                ]
            })
            .overrideTemplate(InfoDefinitionDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InfoDefinitionDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InfoDefinitionService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new InfoDefinition(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.infoDefinition = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'infoDefinitionListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new InfoDefinition();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.infoDefinition = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'infoDefinitionListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
