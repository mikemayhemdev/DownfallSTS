package theHexaghost.cards;

import champ.powers.GladiatorFormPower;
import champ.relics.RageAmulet;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import downfall.downfallMod;
import theHexaghost.HexaMod;
import theHexaghost.util.HexaPurpleTextInterface;

public class SpectersWail extends AbstractHexaCard implements HexaPurpleTextInterface {

    public final static String ID = makeID("SpectersWail");

    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 2;

    public SpectersWail() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = DAMAGE;
        isEthereal = true;
        isMultiDamage = true;
        tags.add(HexaMod.AFTERLIFE);
        HexaMod.loadJokeCardImage(this, "SpectersWail.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SFXAction("ATTACK_PIERCING_WAIL"));
        for (int q = 0; q < 6; q++) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new ShockWaveEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, new Color(MathUtils.random(1.0f), MathUtils.random(1.0f), MathUtils.random(1.0f), 1.0f), ShockWaveEffect.ShockWaveType.NORMAL)));
        }
        allDmg(AbstractGameAction.AttackEffect.FIRE);
        allDmg(AbstractGameAction.AttackEffect.FIRE);
    }

    public void afterlife() {
        this.addToBot(new SFXAction("ATTACK_PIERCING_WAIL"));
        for (int q = 0; q < 6; q++) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new ShockWaveEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, new Color(MathUtils.random(1.0f), MathUtils.random(1.0f), MathUtils.random(1.0f), 1.0f), ShockWaveEffect.ShockWaveType.NORMAL)));
        }
        AbstractPlayer p=AbstractDungeon.player;
        this.applyPowers();
        if(AbstractDungeon.player.hasPower("Pen Nib") ){

            int damages[] = DamageInfo.createDamageMatrix(this.baseDamage);
            for(int i = 0; i < damages.length; i++){
                damages[i] /= 2;
            }
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, damages, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));
        }else {
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(this.baseDamage), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));
        }

        if (AbstractDungeon.player.hasPower(GladiatorFormPower.POWER_ID)) {
            GladiatorFormPower revengePower = (GladiatorFormPower) AbstractDungeon.player.getPower(GladiatorFormPower.POWER_ID);

            if (revengePower != null) {
                revengePower.onSpecificTriggerBranch();
            }
        }
        if (AbstractDungeon.player.hasPower(VigorPower.POWER_ID)) {
            for (AbstractRelic r : AbstractDungeon.player.relics) {
                if (r instanceof RageAmulet) {
                    ((RageAmulet) r).onSpecificTrigger();
                }
            }

            atb(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, VigorPower.POWER_ID));
        }
    }

        public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
        }
    }

    // to still show afterlife tooltip. because the format [purple]hexamod:afterlife[] doesnt get displayed correctly
    // we are only using [purple]afterlife[] here for easier text comprehension for new players, but doing this
    // means we dont have the keyword tooltip so we need to manually add it
    // but after I tried adding it in the constrcutor it turns out sometimes who knows why it wont be added
    // and this way seems to work
    @Override
    public void initializeDescription() {
        super.initializeDescription();
        String afterlife_name = downfallMod.keywords_and_proper_names.get("afterlife");
        this.keywords.add(afterlife_name);
    }
}