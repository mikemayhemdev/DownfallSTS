package automaton.cards;

import automaton.cardmods.EncodeMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.actions.OctoChoiceAction;
import downfall.cards.OctoChoiceCard;
import downfall.util.OctopusCard;
import expansioncontent.expansionContentMod;
import sneckomod.actions.ChangeGoldAction;

import java.util.ArrayList;

public class Branch extends AbstractBronzeCard implements OctopusCard {

    public final static String ID = makeID("Branch");

    //stupid intellij stuff attack, self_and_enemy, common

    private static final int DAMAGE = 9;
    private static final int BLOCK = 9;

    public Branch() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.SELF_AND_ENEMY);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
        CardModifierManager.addModifier(this, new EncodeMod());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new OctoChoiceAction(m, this));
    }

    public ArrayList<OctoChoiceCard> choiceList() {
        ArrayList<OctoChoiceCard> cardList = new ArrayList<>();
        cardList.add(new OctoChoiceCard("bronze:BranchHit", EXTENDED_DESCRIPTION[0], expansionContentMod.makeCardPath("KnowingSkullWish.png"), EXTENDED_DESCRIPTION[1], damage, -1));
        cardList.add(new OctoChoiceCard("bronze:BranchBlock", EXTENDED_DESCRIPTION[0], expansionContentMod.makeCardPath("KnowingSkullWish.png"), EXTENDED_DESCRIPTION[1], -1, block));
        return cardList;
    }

    public void doChoiceStuff(AbstractMonster m, OctoChoiceCard card) {
        switch (card.cardID) {
            case "bronze:BranchHit":{
                att(new DamageAction(m, new DamageInfo(AbstractDungeon.player, card.baseDamage, card.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                break;
            }
            case "bronze:BranchBlock":{
                att(new GainBlockAction(AbstractDungeon.player, card.baseBlock));
            }
        }
    }

    public void upp() {
        upgradeDamage(2);
        upgradeBlock(2);
    }
}