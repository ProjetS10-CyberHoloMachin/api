/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MicroTestModule } from '../../../test.module';
import { BuildingDataDefinitionComponent } from '../../../../../../main/webapp/app/entities/building-data-definition/building-data-definition.component';
import { BuildingDataDefinitionService } from '../../../../../../main/webapp/app/entities/building-data-definition/building-data-definition.service';
import { BuildingDataDefinition } from '../../../../../../main/webapp/app/entities/building-data-definition/building-data-definition.model';

describe('Component Tests', () => {

    describe('BuildingDataDefinition Management Component', () => {
        let comp: BuildingDataDefinitionComponent;
        let fixture: ComponentFixture<BuildingDataDefinitionComponent>;
        let service: BuildingDataDefinitionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MicroTestModule],
                declarations: [BuildingDataDefinitionComponent],
                providers: [
                    BuildingDataDefinitionService
                ]
            })
            .overrideTemplate(BuildingDataDefinitionComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BuildingDataDefinitionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BuildingDataDefinitionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new BuildingDataDefinition(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.buildingDataDefinitions[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
