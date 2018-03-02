/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { MicroTestModule } from '../../../test.module';
import { BuildingDataDefinitionDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/building-data-definition/building-data-definition-delete-dialog.component';
import { BuildingDataDefinitionService } from '../../../../../../main/webapp/app/entities/building-data-definition/building-data-definition.service';

describe('Component Tests', () => {

    describe('BuildingDataDefinition Management Delete Component', () => {
        let comp: BuildingDataDefinitionDeleteDialogComponent;
        let fixture: ComponentFixture<BuildingDataDefinitionDeleteDialogComponent>;
        let service: BuildingDataDefinitionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MicroTestModule],
                declarations: [BuildingDataDefinitionDeleteDialogComponent],
                providers: [
                    BuildingDataDefinitionService
                ]
            })
            .overrideTemplate(BuildingDataDefinitionDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BuildingDataDefinitionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BuildingDataDefinitionService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
