package sneckomod.cards;

import champ.cards.Strike;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.CripplingPoison;
import com.megacrit.cardcrawl.cards.green.PoisonedStab;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.FrozenEgg2;
import com.megacrit.cardcrawl.relics.MoltenEgg2;
import com.megacrit.cardcrawl.relics.ToxicEgg2;
import com.megacrit.cardcrawl.vfx.CollectorCurseEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import hermit.cards.HoleUp;
import sneckomod.SneckoMod;
import sneckomod.powers.LacerateDebuff;
import sneckomod.powers.VenomDebuff;
import sneckomod.relics.UnknownEgg;

import java.util.ArrayList;

public class Lacerate extends AbstractSneckoCard {

    public static final String ID = SneckoMod.makeID("Lacerate");

    private static final int DAMAGE = 8;
    private static final int COST = 1;
    private static final int UPGRADE_MAGIC = 1;
    private static final int MAGIC = 3;

    public Lacerate() {
        super(ID, COST, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        //baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        this.cardsToPreview = new PoisonedStab();
        SneckoMod.loadJokeCardImage(this, "Lacerate.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if ((!monster.isDead) && (!monster.isDying) && !monster.halfDead) {
                    addToBot(new ApplyPowerAction(monster, p, new VenomDebuff(monster, magicNumber), magicNumber));
                }
            }
        }
        AbstractCard g = new PoisonedStab();
        if (this.upgraded) {
            g.upgrade();
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(g));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
        }
    }
}
