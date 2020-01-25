package guardian.powers;


import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;


public class DefensiveModeBuffsPower extends AbstractGuardianPower {
    public static final String POWER_ID = "Guardian:DefensiveModeBuffsPower";
    public static PowerType POWER_TYPE = PowerType.BUFF;

    public static String[] DESCRIPTIONS;
    public int thorns;
    public int dexterity;
    public int metallicize;
    public int statusNegation;
    public int enrage;


    public DefensiveModeBuffsPower(AbstractCreature owner, AbstractCreature source, int thorns, int dexterity, int metallicize, int statusNegation, int enrage) {

        this.ID = POWER_ID;
        this.owner = owner;
        this.setImage("DefenseModeBuffsPower84.png", "DefenseModeBuffsPower32.png");
        this.type = POWER_TYPE;
        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;
        this.thorns = thorns;
        this.dexterity = dexterity;
        this.metallicize = metallicize;
        this.statusNegation = statusNegation;
        this.enrage = enrage;

        updateDescription();

    }

    public void updateDescription() {
        String desc;
        desc = DESCRIPTIONS[0];
        if (this.thorns > 0){
            desc += this.thorns + DESCRIPTIONS[1];
            if (this.dexterity > 0 || this.metallicize > 0 || this.statusNegation > 0 || this.enrage > 0) {
                desc += " NL ";
            }
        }
        if (this.dexterity > 0){
            desc += this.dexterity + DESCRIPTIONS[2];
            if (this.metallicize > 0 || this.statusNegation > 0 || this.enrage > 0) {
                desc += " NL ";
            }
        }
        if (this.metallicize > 0){
            desc += this.metallicize + DESCRIPTIONS[3];
            if (/*this.statusNegation > 0 || */this.enrage > 0) {
                desc += " NL ";
            }
        }
        /*
        if (this.statusNegation > 0){
            if (this.statusNegation == 1){
                desc += DESCRIPTIONS[4];
            } else {
                desc += DESCRIPTIONS[5] + this.statusNegation + DESCRIPTIONS[6];

            }
            if (this.enrage > 0) {
                desc += " NL ";
            }
        }
        */
        if (this.enrage > 0){
            desc += DESCRIPTIONS[7] + this.enrage + DESCRIPTIONS[8];
        }

            this.description = desc;

    }

}