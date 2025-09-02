package charbosses.powers.general;

import basemod.ReflectionHacks;
import basemod.interfaces.CloneablePowerInterface;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Hermit.CharBossHermit;
import charbosses.bosses.Hermit.Simpler.ArchetypeAct2WheelOfFateSimple;
import charbosses.bosses.Silent.CharBossSilent;
import charbosses.bosses.Silent.Simpler.ArchetypeAct1PoisonSimple;
import charbosses.cards.other.Antidote;
import charbosses.powers.bossmechanicpowers.AbstractBossMechanicPower;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.GainPowerEffect;
import downfall.downfallMod;
import downfall.monsters.NeowBossFinal;
import downfall.util.TextureLoader;
import slimebound.SlimeboundMod;

import java.util.ArrayList;
import java.util.Iterator;

import static hermit.util.Wiz.atb;

public class PoisonProtectionPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = downfallMod.makeID("PoisonResist");
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String DESCRIPTIONS[] = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/PoisonResist84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/PoisonResist32.png"));

    public static boolean CANNOT_END = false;

    private float timer;
    private boolean firstTurn = true;

    public PoisonProtectionPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.name = NAME;

        this.updateDescription();
    }

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (damageAmount > 0 && target != this.owner && info.type == DamageInfo.DamageType.NORMAL) {
            this.flash();

            if(AbstractDungeon.player.hasPower(EnemyPoisonPower.POWER_ID) && AbstractDungeon.player.getPower(EnemyPoisonPower.POWER_ID).amount <= this.amount){
                atb(new RemoveSpecificPowerAction(owner, owner, EnemyPoisonPower.POWER_ID));

            }else{
                addToBot(new ReducePowerAction(owner, owner, EnemyPoisonPower.POWER_ID, this.amount));
            }
        }

    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new PoisonProtectionPower(owner, amount);
    }
}