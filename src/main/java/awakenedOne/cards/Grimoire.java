package awakenedOne.cards;

import awakenedOne.actions.ConjureAction;
import awakenedOne.cards.tokens.spells.AphoticShield;
import awakenedOne.relics.EyeOfTheOccult;
import awakenedOne.ui.OrbitingSpells;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import com.megacrit.cardcrawl.vfx.combat.ReaperEffect;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.ui.OrbitingSpells.spellCards;
import static awakenedOne.ui.OrbitingSpells.updateTimeOffsets;
import static awakenedOne.util.Wiz.vfx;

public class Grimoire extends AbstractAwakenedCard {
    public final static String ID = makeID(Grimoire.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 9, 1, , , 3, 1

    public Grimoire() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 7;
        baseMagicNumber = magicNumber = 7;
        this.exhaust = true;
        loadJokeCardImage(this, makeBetaCardPath(Grimoire.class.getSimpleName() + ".png"));
    }


    @Override
    public void applyPowers() {
        super.applyPowers();
        if (this.hasTag(SPELLCARD)) {
            if (AbstractDungeon.player.hasRelic(EyeOfTheOccult.ID)) {
                target = CardTarget.ALL_ENEMY;
            }
        }
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!this.hasTag(SPELLCARD)) {
            dmg(m, AbstractGameAction.AttackEffect.FIRE);
        } else {
            if (!AbstractDungeon.player.hasRelic(EyeOfTheOccult.ID)) {
                dmg(m, AbstractGameAction.AttackEffect.FIRE);
            } else {
                //AbstractDungeon.player.getRelic(EyeOfTheOccult.ID).flash();
                this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
            }
        }
        AbstractCard newcard = this.makeStatEquivalentCopy();
        this.addToTop(new ModifyDamageAction(newcard.uuid, this.magicNumber));
        spellCards.add(new OrbitingSpells.CardRenderInfo(newcard));
        updateTimeOffsets();
    }

    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(2);
    }
}