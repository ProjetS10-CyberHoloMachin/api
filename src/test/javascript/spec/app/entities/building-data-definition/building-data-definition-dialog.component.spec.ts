/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { MicroTestModule } from '../../../test.module';
import { BuildingDataDefinitionDialogComponent } from '../../../../../../main/webapp/app/entities/building-data-definition/building-data-definition-dialog.component';
import { BuildingDataDefinitionService } from '../../../../../../main/webapp/app/entities/building-data-definition/building-data-definition.service';
import { BuildingDataDefinition } from '../../../../../../main/webapp/app/entities/building-data-definition/building-data-definition.model';

describe('Component Tests', () => {

    describe('BuildingDataDefinition Management Dialog Component', () => {
        let comp: BuildingDataDefinitionDialogComponent;
        let fixture: ComponentFixture<BuildingDataDefinitionDialogComponent>;
        let service: BuildingDataDefinitionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MicroTestModule],
                declarations: [BuildingDataDefinitionDialogComponent],
                providers: [
                    BuildingDataDefinitionService
                ]
            })
            .overrideTemplate(BuildingDataDefinitionDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BuildingDataDefinitionDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BuildingDataDefinitionService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new BuildingDataDefinition(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.buildingDataDefinition = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'buildingDataDefinitionListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new BuildingDataDefinition();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.buildingDataDefinition = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'buildingDataDefinitionListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
