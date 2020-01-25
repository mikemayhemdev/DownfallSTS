package guardian.powers;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import guardian.characters.GuardianCharacter;


public class zzzGuardianModePower extends AbstractGuardianPower {
    public static final String POWER_ID = "Guardian:GuardianModePowerOffense";

    public static PowerType POWER_TYPE = PowerType.BUFF;

    public static String[] DESCRIPTIONS;
    public static String[] DESCRIPTIONSdef;

    public boolean inDefensive = false;
    private String IDdef = "Guardian:GuardianModePowerDefense";

    public zzzGuardianModePower(AbstractCreature owner) {

       this.ID = POWER_ID;
        this.IDdef = POWER_ID;
        this.owner = owner;
        this.setImage("OffenseModePower84.png", "OffenseModePower32.png");
        this.type = POWER_TYPE;
        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.DESCRIPTIONSdef = CardCrawlGame.languagePack.getPowerStrings(this.IDdef).DESCRIPTIONS;

        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        updateDescription();

    }

    public void updateDescription() {
        if (inDefensive){
            this.description = DESCRIPTIONSdef[0];

        } else {
            this.description = DESCRIPTIONS[0];

        }

    }

    public void onInitialApplication() {
        super.onInitialApplication();
        if (this.owner instanceof GuardianCharacter){
            ((GuardianCharacter)this.owner).switchToOffensiveMode();
        }
    }

    public void atStartOfTurn() {
        //AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, this.amount));
       // AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.amount));
    }

    public void switchToDefensiveMode(){
        if (this.owner instanceof GuardianCharacter){
            ((GuardianCharacter)this.owner).switchToDefensiveMode();
        }
        this.inDefensive = true;
        this.setImage("DefenseModePower84.png", "DefenseModePower32.png");
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.IDdef).NAME;
       // if (GuardianMod.bronzeOrbInPlay != null) {
        //    GuardianMod.bronzeOrbInPlay.moveToFrontline();
       // }
        updateDescription();
    }

    public void switchToOffensiveMode(){

        if (this.owner instanceof GuardianCharacter){
            ((GuardianCharacter)this.owner).switchToOffensiveMode();
        }
        this.inDefensive = false;
        this.setImage("OffenseModePower84.png", "OffenseModePower32.png");
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;
       // if (GuardianMod.bronzeOrbInPlay != null) {
       //     GuardianMod.bronzeOrbInPlay.moveToBackline();
       // }
        updateDescription();
    }

    public static class BronzeOrbBlockPower extends AbstractGuardianPower {
        public static final String POWER_ID = "Guardian:BronzeOrbBlockPower";

        public static PowerType POWER_TYPE = PowerType.BUFF;

        public static String[] DESCRIPTIONS;

        public boolean isActive;

        public BronzeOrbBlockPower(AbstractCreature owner, int amount) {

           this.ID = POWER_ID;
            this.owner = owner;

            this.type = POWER_TYPE;
            this.amount = amount;
            this.loadRegion("dexterity");

            this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;

            this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

            updateDescription();

        }

        public void updateDescription() {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];

        }

    }

    public static class BronzeOrbDamagePower extends AbstractGuardianPower {
        public static final String POWER_ID = "Guardian:BronzeOrbDamagePower";

        public static PowerType POWER_TYPE = PowerType.BUFF;

        public static String[] DESCRIPTIONS;

        public boolean isActive;

        public BronzeOrbDamagePower(AbstractCreature owner, int amount) {

           this.ID = POWER_ID;
            this.owner = owner;
            this.loadRegion("strength");
            this.amount = amount;
            this.type = POWER_TYPE;
            this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;


            this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

            updateDescription();

        }

        public void updateDescription() {
                this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];

        }

    }

    public static class BronzeOrbExplodePower extends AbstractGuardianTwoAmountPower {
        public static final String POWER_ID = "Guardian:BronzeOrbExplodePower";

        public static PowerType POWER_TYPE = PowerType.BUFF;

        public static String[] DESCRIPTIONS;



        public BronzeOrbExplodePower(AbstractCreature owner, int explodeAmount, int turns) {

           this.ID = POWER_ID;
            this.owner = owner;
            this.loadRegion("explosive");

            this.type = POWER_TYPE;
            this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
            this.amount2 = turns;
            this.amount = explodeAmount;
            this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

            updateDescription();

        }

        public void updateDescription() {
            if (this.amount2 > 1){
                this.description = DESCRIPTIONS[2] + this.amount2 + DESCRIPTIONS[3] + this.amount + DESCRIPTIONS[1];
            } else {
                this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];

            }
        }

        public void updateExplode(){
            if (this.amount2 == 1 && !this.owner.isDying) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new ExplosionSmallEffect(this.owner.hb.cX, this.owner.hb.cY), 0.1F));
                AbstractDungeon.actionManager.addToBottom(new SuicideAction((AbstractMonster)this.owner));
                DamageInfo damageInfo = new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS);
                for (AbstractMonster m: AbstractDungeon.getMonsters().monsters) {
                    if (!m.isDead && !m.isDying) {
                        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, damageInfo, AbstractGameAction.AttackEffect.FIRE, true));
                    }
                }
                } else {
                this.amount2--;
                this.updateDescription();
            }
        }

        public void atStartOfTurn() {
            //AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, this.amount));
           // AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.amount));
        }

    }

    public static class BronzeOrbLocationPower extends AbstractGuardianPower {
        public static final String POWER_ID = "Guardian:BronzeOrbLocationPowerFront";

        public static PowerType POWER_TYPE = PowerType.BUFF;

        public static String[] DESCRIPTIONS;
        public static String[] DESCRIPTIONSdef;

        public boolean inRear = false;
        private String IDdef = "Guardian:BronzeOrbLocationPowerBack";



        public BronzeOrbLocationPower(AbstractCreature owner) {

           this.ID = POWER_ID;
            this.owner = owner;
            this.setImage("bronzeOrbProtectionPower84.png", "bronzeOrbProtectionPower32.png");

            this.type = POWER_TYPE;
            this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
            this.DESCRIPTIONSdef = CardCrawlGame.languagePack.getPowerStrings(this.IDdef).DESCRIPTIONS;

            this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

            updateDescription();

        }

        public void updateDescription() {
            if (inRear){
                this.description = DESCRIPTIONSdef[0];

            } else {
                this.description = DESCRIPTIONS[0];

            }

        }

        public void moveToBackline(){
            this.inRear = true;
            this.setImage("bronzeOrbProtectionPowerInactive84.png", "bronzeOrbProtectionPowerInactive32.png");
            this.name = CardCrawlGame.languagePack.getPowerStrings(this.IDdef).NAME;

            updateDescription();
        }

        public void moveToFrontline(){
            this.inRear = false;
            this.setImage("bronzeOrbProtectionPower84.png", "bronzeOrbProtectionPower32.png");
            this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

            updateDescription();
        }


        public void atStartOfTurn() {
            //AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, this.amount));
           // AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.amount));
        }

    }

    public static class BronzeOrbProtectionPower extends AbstractGuardianPower {
        public static final String POWER_ID = "Guardian:BronzeOrbProtectionPower";

        public static PowerType POWER_TYPE = PowerType.BUFF;

        public static String[] DESCRIPTIONS;

        public boolean isActive;

        public BronzeOrbProtectionPower(AbstractCreature owner) {

           this.ID = POWER_ID;
            this.owner = owner;
            this.setImage("bronzeOrbProtectionPower84.png", "bronzeOrbProtectionPower32.png");

            this.type = POWER_TYPE;
            this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;


            this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

            updateDescription();

        }

        public void updateDescription() {
            if (isActive){
                this.description = DESCRIPTIONS[0];

            } else {
                this.description = DESCRIPTIONS[1];

            }

        }

        public void setActive(){
            this.isActive = true;
            this.setImage("bronzeOrbProtectionPower84.png", "bronzeOrbProtectionPower32.png");

            updateDescription();
        }

        public void setInactive(){
            this.isActive = false;
            this.setImage("bronzeOrbProtectionPowerInactive84.png", "bronzeOrbProtectionPowerInactive32.png");

            updateDescription();
        }

        @Override
        public int onAttacked(DamageInfo info, int damageAmount) {
            if (this.isActive) {
                damageAmount = damageAmount / 2;
                info.base = info.base /2;
                /*
                if (GuardianMod.bronzeOrbInPlay != null) {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(GuardianMod.bronzeOrbInPlay, new DamageInfo(info.owner, damageAmount, info.type), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                } else {
                    GuardianMod.logger.info("ERROR - Bronze Orb power cut damage in half but there is no Bronze Orb in play");
                }
                */
            }

            return damageAmount;
        }
    }

    public static class BronzeOrbWeakenPower extends AbstractGuardianPower {
        public static final String POWER_ID = "Guardian:BronzeOrbWeakenPower";

        public static PowerType POWER_TYPE = PowerType.BUFF;

        public static String[] DESCRIPTIONS;

        public boolean isActive;

        public BronzeOrbWeakenPower(AbstractCreature owner, int amount) {

           this.ID = POWER_ID;
            this.owner = owner;

            this.type = POWER_TYPE;
            this.amount = amount;
            this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
            this.setImage("BronzeOrbWeaken84.png", "BronzeOrbWeaken32.png");

            this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

            updateDescription();

        }

        public void updateDescription() {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];

        }

    }
}
