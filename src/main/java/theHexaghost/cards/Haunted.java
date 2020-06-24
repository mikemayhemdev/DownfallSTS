package theHexaghost.cards;


import basemod.abstracts.CustomCard;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.EtherealMod;
import guardian.GuardianMod;
import slimebound.SlimeboundMod;
import slimebound.cards.AbstractSlimeboundCard;
import theHexaghost.HexaMod;
import theHexaghost.actions.HauntedAction;

import java.util.ArrayList;


public class Haunted extends CustomCard {
    public static final String ID = HexaMod.makeID("Haunted");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "haunted.png";
    private static final CardType TYPE = CardType.CURSE;
    private static final CardRarity RARITY = CardRarity.CURSE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardStrings cardStrings;
    private static final int COST = -2;
    private static final int BLOCK = 5;
    private static final int UPGRADE_BONUS = 3;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public Haunted() {
        super(ID, NAME, HexaMod.makeCardPath(IMG_PATH), COST, DESCRIPTION, TYPE, CardColor.CURSE, RARITY, TARGET);

        this.magicNumber = this.baseMagicNumber = 1;

        this.isEthereal = true;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                for (AbstractCard c : AbstractDungeon.player.hand.group) {
                    if (!c.isEthereal) {
                        CardModifierManager.addModifier(c, new EtherealMod());
                        c.superFlash(Color.PURPLE.cpy());
                    }
                }
            }
        });

    }

    @Override
    public void atTurnStart() {
    }

    public AbstractCard makeCopy() {
        return new Haunted();
    }

    public void upgrade() {
    }
}


