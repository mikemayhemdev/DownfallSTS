package expansioncontent.cards;

import automaton.actions.AddToFuncAction;
import automaton.cards.BranchBlock;
import automaton.cards.BranchHit;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;
import downfall.actions.OctoChoiceAction;
import downfall.cards.OctoChoiceCard;
import downfall.util.OctopusCard;
import expansioncontent.expansionContentMod;
import expansioncontent.powers.AddSameInstancePower;
import expansioncontent.powers.NextTurnExhumePower;

import java.util.ArrayList;

public class ShapePower extends AbstractExpansionCard implements OctopusCard {
    public final static String ID = makeID("ShapePower");

    private static final int DAMAGE_INCREASE = 1;

    public ShapePower() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_donudeca.png", "expansioncontentResources/images/1024/bg_boss_donudeca.png");
        tags.add(expansionContentMod.STUDY_SHAPES);
        tags.add(expansionContentMod.STUDY);
        baseMagicNumber = 2;
        baseBlock = block = 5;
        //partner is null until this card is played in combat
        expansionContentMod.loadJokeCardImage(this, "PolyBeam.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new OctoChoiceAction(m, this));
    }

    public ArrayList<OctoChoiceCard> choiceList() {
        ArrayList<OctoChoiceCard> cardList = new ArrayList<>();
        cardList.add(new OctoChoiceCard("expansioncontent:PolyBeam", EXTENDED_DESCRIPTION[0], "expansioncontentResources/images/cards/PolyBeam.png", EXTENDED_DESCRIPTION[1], -1, -1, magicNumber, CardType.ATTACK));
        cardList.add(new OctoChoiceCard("expansioncontent:DecaShield", EXTENDED_DESCRIPTION[2], "expansioncontentResources/images/cards/DecaShield.png", EXTENDED_DESCRIPTION[3], -1, block, CardType.SKILL));
        return cardList;
    }

    public void doChoiceStuff(AbstractMonster m, OctoChoiceCard card) {
        AbstractPlayer p = AbstractDungeon.player;
        switch (card.cardID) {
            case "expansioncontent:PolyBeam": {
                   atb(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber), magicNumber));
                 break;
            }
            case "expansioncontent:DecaShield": {
                atb(new ApplyPowerAction(p, p, new PlatedArmorPower(p, block)));
            }
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        block = baseBlock;
        isBlockModified = false;
    }

    @Override
    public void upgrade() {
        upgradeBlock(3);
        upgradeMagicNumber(1);
    }

}