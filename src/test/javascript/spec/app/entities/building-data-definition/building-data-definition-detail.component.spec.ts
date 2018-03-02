/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { MicroTestModule } from '../../../test.module';
import { BuildingDataDefinitionDetailComponent } from '../../../../../../main/webapp/app/entities/building-data-definition/building-data-definition-detail.component';
import { BuildingDataDefinitionService } from '../../../../../../main/webapp/app/entities/building-data-definition/building-data-definition.service';
import { BuildingDataDefinition } from '../../../../../../main/webapp/app/entities/building-data-definition/building-data-definition.model';

describe('Component Tests', () => {

    describe('BuildingDataDefinition Management Detail Component', () => {
        let comp: BuildingDataDefinitionDetailComponent;
        let fixture: ComponentFixture<BuildingDataDefinitionDetailComponent>;
        let service: BuildingDataDefinitionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MicroTestModule],
                declarations: [BuildingDataDefinitionDetailComponent],
                providers: [
                    BuildingDataDefinitionService
                ]
            })
            .overrideTemplate(BuildingDataDefinitionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BuildingDataDefinitionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BuildingDataDefinitionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new BuildingDataDefinition(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.buildingDataDefinition).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
