//package sneckomod.powers;
//
//import basemod.interfaces.CloneablePowerInterface;
//import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
//import com.megacrit.cardcrawl.core.AbstractCreature;
//import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.TextureAtlas;
//import com.megacrit.cardcrawl.core.CardCrawlGame;
//import com.megacrit.cardcrawl.localization.PowerStrings;
//import com.megacrit.cardcrawl.powers.AbstractPower;
//import downfall.util.TextureLoader;
//import sneckomod.SneckoMod;
//
//public class ToxicPersonalityPower extends AbstractPower implements CloneablePowerInterface {
//    public static final String POWER_ID = SneckoMod.makeID("ToxicPersonalityPower");
//    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
//    public static final String NAME = powerStrings.NAME;
//    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
//
//    private static final Texture tex84 = TextureLoader.getTexture(SneckoMod.getModID() + "Resources/images/powers/Rolls84.png");
//    private static final Texture tex32 = TextureLoader.getTexture(SneckoMod.getModID() + "Resources/images/powers/Rolls32.png");
//
//    public ToxicPersonalityPower(int amount) {
//        this.name = NAME;
//        this.ID = POWER_ID;
//        this.owner = AbstractDungeon.player;
//        this.amount = amount;
//        this.type = PowerType.BUFF;
//        this.isTurnBased = false;
//
//        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
//        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
//
//        this.updateDescription();
//    }
//
//    @Override
//    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
//        // Check if the applied power is a debuff and not Venom/Shackle AND they don't have Artifact
//        if ((power.type == PowerType.DEBUFF && !(power instanceof VenomDebuff) && source == this.owner && target != this.owner) && !power.ID.equals("Shackled") && !target.hasPower("Artifact")){
//            onActivateCall(target);
//        }
//    }
//
//    public void onActivateCall(AbstractCreature target) {
//        this.flash();
//        this.addToBot(new ApplyPowerAction(target, this.owner, new VenomDebuff(target, this.amount), this.amount));
//    }
//
//
//
//
//    @Override
//    public void updateDescription() {
//        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
//    }
//
//    @Override
//    public AbstractPower makeCopy() {
//        return new ToxicPersonalityPower(this.amount);
//    }
//}